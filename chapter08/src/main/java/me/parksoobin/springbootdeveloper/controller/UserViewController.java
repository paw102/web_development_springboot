package me.parksoobin.springbootdeveloper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
}
/*
    GET 요청으로 /login 경로로 돌아오면 login() 메서드가 login.html 을, 마찬가지 요청으로 /signup 경로로 들어오면
    sinup() 메서드가 signup.html 로 들어오는 경로 생성

    resources/templates -> login.html 생성
 */