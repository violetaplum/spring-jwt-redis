package com.toy.member.jwt.controller;

import com.toy.member.jwt.domain.Member;
import com.toy.member.jwt.domain.dto.MemberDto;
import com.toy.member.jwt.domain.dto.MemberLoginDto;
import com.toy.member.jwt.domain.dto.Response;
import com.toy.member.jwt.service.MemberService;
import com.toy.member.jwt.utils.CookieUtils;
import com.toy.member.jwt.utils.JwtUtils;
import com.toy.member.jwt.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final CookieUtils cookieUtils;
    private final RedisUtils redisUtils;



    @PostMapping("/api/sign-up") // POST 방식용 매핑
    // @RequestBody 는 POST 요청시 붙여야하는 어노테이션
    public ResponseEntity<Object> createMember(@RequestBody @Validated MemberDto memberDto){

        ModelMapper modelMapper= new ModelMapper();

        memberService.checkMemberId(memberDto.getMemberId());

        Member member = modelMapper.map(memberDto,Member.class);

        // db 에 넣을때 암호화 해주는용도
        String password = passwordEncoder.encode(member.getMemberPassword());
        member.setMemberPassword(password);
        member.setType(Member.RoleType.ROLE_USER);

        memberService.saveMember(member);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PostMapping("/api/login/process")
    public Response login(@RequestBody MemberLoginDto user, HttpServletRequest req, HttpServletResponse res){
        final Member member = memberService.loginMember(user.getUsername(), user.getPassword());
        final String token = jwtUtils.generateToken(member);
        final String refreshJwt = jwtUtils.generateRefreshToken(member);
        Cookie accessToken = cookieUtils.createCookie(JwtUtils.ACCESS_TOKEN_NAME, token);
        Cookie refreshToken = cookieUtils.createCookie(JwtUtils.REFRESH_TOKEN_NAME, refreshJwt);
        redisUtils.setDataExpire(refreshJwt, member.getMemberId(), JwtUtils.REFRESH_TOKEN_EXPIRE_TIME);
        res.addCookie(accessToken);
        res.addCookie(refreshToken);
        return new Response("success", "로그인에 성공했습니다.", token);
    }

}
