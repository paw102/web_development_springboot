package me.parksoobin.springbootdeveloper.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller     // 컨트롤러 명시
public class ExampleController {

    @GetMapping("thymeleaf/example")
    public String thymeleafExample(Model model){ // import org.springframework.ui.Model;
        // 뷰 (우리가 보는 화면) 로 데이터를 넘겨주는 모델 객체
        Person examplePerson = new Person();
        // id 1L, 이름에 홍길동, 나이에 11, 취미에 운동, 독서 입력하세요
        examplePerson.setId(1L);
        examplePerson.setName("홍길동");
        examplePerson.setAge(11);
        examplePerson.setHobbies(List.of("운동","독서"));

        // 이상에서 Person 클래스읭 인스턴스에 값 대입
        model.addAttribute("person", examplePerson);    // Person 객체를 "person" 키에 저장
        model.addAttribute("today", LocalDate.now());

        return "example";   // example.html 이라는 뷰를 조회합니다.
    }

    @Setter
    @Getter
    class Person {
        private long id;
        private String name;
        private int age;
        private List<String> hobbies;

    }

}
/*
    Model 객체는 뷰, 즉 HTML 쪽으로 값을 넘겨주는 객체입니다. 모델 객체는 따로 생성할 필요 없이 코드처럼
    argument 로 선언하기만 하면 스프링이 알아서 만들어주므로 편리하게 사용 가능

    "Person" 이라는 키에 사람 정보를, "today" 라는 키에 날짜 정보를 저장.

    thymeleafExample() 이라는 메서드는 "example" 이라는 리턴값을 가지는데, 애가 @Controller 라는 애너테이션과 합쳐지면
    뷰의 이름이라는 의미가 됩니다.
    즉, 스프링 부트는 Controller 의 @Controller 애너테이션을 보고
    '리턴 값의 이름 을 가진 뷰의 파일을 찾으라고' 알아듣고서
    resources/templates 디렉터리에서 example.html 을 찾은 당므에 웹 브라우저에 해당 파일을 보여줍니다.
 */