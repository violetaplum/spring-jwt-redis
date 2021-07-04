package com.toy.member.jwt.security.handler;

import com.toy.member.jwt.model.Member;
import com.toy.member.jwt.utils.CookieUtils;
import com.toy.member.jwt.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CookieUtils cookieUtils;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("onAuthenticationSuccess!!!");
        String username = authentication.getName();

        Member member = new Member();
        member.setMemberId(username);

        final String token = jwtUtils.generateToken(member);
        final String refreshJwt = jwtUtils.generateRefreshToken(member);
        Cookie accessToken = cookieUtils.createCookie(JwtUtils.ACCESS_TOKEN_NAME, token);
        Cookie refreshToken = cookieUtils.createCookie(JwtUtils.REFRESH_TOKEN_NAME, refreshJwt);

        response.addCookie(accessToken);
        response.addCookie(refreshToken);

        setDefaultTargetUrl("/main");
        SavedRequest savedRequest = requestCache.getRequest(request,response);

        if(savedRequest != null){
            String targetUrl = savedRequest.getRedirectUrl();
            redirectStrategy.sendRedirect(request,response,targetUrl);
        }else{
            redirectStrategy.sendRedirect(request,response,getDefaultTargetUrl());
        }

    }

}