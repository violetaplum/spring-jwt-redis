package com.toy.member.jwt.utils;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class CookieUtils {
    public Cookie createCookie(String cookieName, String value){
        Cookie token = new Cookie(cookieName,value);
        token.setHttpOnly(true);
        token.setMaxAge((int)JwtUtils.TOKEN_EXPIRE_TIME);
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
