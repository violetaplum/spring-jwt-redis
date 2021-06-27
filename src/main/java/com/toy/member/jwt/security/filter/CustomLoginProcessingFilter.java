package com.toy.member.jwt.security.filter;

import com.toy.member.jwt.model.dto.MemberDto;
import com.toy.member.jwt.security.token.CustomAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    public CustomLoginProcessingFilter() {
        super(new AntPathRequestMatcher("/api/login/process"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if (isAjax(request)) {
            throw new IllegalStateException("Authentication is not supported");
        }


        MemberDto memberDto = objectMapper.readValue(request.getReader(), MemberDto.class);

        if (StringUtils.isEmpty(memberDto.getMemberId()) || StringUtils.isEmpty(memberDto.getMemberPassword())) {
            throw new IllegalStateException(("UserName or Password is empty"));
        }

        CustomAuthenticationToken customAuthenticationToken = new CustomAuthenticationToken(memberDto.getMemberId(), memberDto.getMemberPassword());


        return getAuthenticationManager().authenticate(customAuthenticationToken);
    }

    private Boolean isAjax(HttpServletRequest request) {
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-with"))) {
            return true;
        }
        return false;
    }
}

