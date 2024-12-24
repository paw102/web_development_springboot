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