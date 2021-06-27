package com.toy.member.jwt.controller;

import com.toy.member.jwt.model.Member;
import com.toy.member.jwt.model.dto.MemberDto;
import com.toy.member.jwt.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MemberApiController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;


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
}
