package me.parksoobin.springbootdeveloper;
/*
    모든 프로젝트는 main 에 해당하는 클래스가 존재 -> 실행용 클래스
    이제 이 class 를 main 클래스로 사용 할 예정임.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class, args);
    }
}

/*
    처음으로 SpringBootDeveloperApplication 파일을 실행시키면 WhiteLabel error page 가 뜸
    현재 요청에 해당하는 페이지가 존재하지 않기 때문에 생겨난 문제임.
    -> 하지만 Spring Application 은 실행됨.

    그래서 error 페이지가 기분 나쁘니 기본적으로 실행될 때의 default 페이지 하나 생성

    20241223
        1. IntelliJ Community Version 설치 -> bin.PATH 지역 변수 생성
        2. Git 설치
        3. Github 연동 -> web_development_springboot -> 지금 문제 있음
        4. IntelliJ 에 Gradle 및 SpringBoot 프로젝트 생성
        5. POSTMAN 설치
 */