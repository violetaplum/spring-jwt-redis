package com.toy.member.jwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/sign-up")
    public String signup(){
        return "signup";
    }

    @GetMapping("/main")
    public String main(){
        return "main";
    }

    @GetMapping("/access-deny")
    public String accessDenied(){
        return "access_deny";
    }

    // admin 에게만 권한 부여됨
    @GetMapping("/my-page")
    public String myPage(){
        return "mypage";
    }
}
