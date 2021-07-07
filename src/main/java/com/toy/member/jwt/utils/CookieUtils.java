package com.toy.member.jwt.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieUtils {
    @Value("${spring.jwt.token-expire-time}")
    private Long TOKEN_EXPIRE_TIME;

    public Cookie createCookie(String cookieName, String value){
        Cookie token = new Cookie(cookieName,value);
        token.setHttpOnly(true);
        token.setMaxAge(TOKEN_EXPIRE_TIME.intValue());
        token.setPath("/");

        return token;
    }

    public Cookie getCookie(HttpServletRequest req, String cookieName){
        final  Cookie[] cookies = req.getCookies();
        if(ObjectUtils.isEmpty(cookies)) return null;

        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName)) return cookie;
        }
        return null;
    }

}
