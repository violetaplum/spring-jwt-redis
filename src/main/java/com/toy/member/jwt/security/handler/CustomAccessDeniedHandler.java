package com.toy.member.jwt.security.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final String errPage;

    public CustomAccessDeniedHandler(String errPage) {
        this.errPage = errPage;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        String deniedUrl = errPage+"?exception="+accessDeniedException.getMessage();

        response.sendRedirect(deniedUrl);
    }

}
