package me.ahngeunsu.springbootdeveloper;
/*
    Test 클래스를 만드는 방법

    1) 테스트하고자 하는 클래스 (main/java 내에 있는 클래스) 를 엽니다.
    2) public class 클래스명이 있는 곳에 클래스 명을 클릭
    3) alt + enter 누르면 팝업이 나옵니다
    4) create test 선택
    5) 저희 프로젝트 상으로는 JUnit5 로 고정되어 있습니다.
 */

import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest // 테스트용 애너테이션 컨텍스트 생성
@AutoConfigureMockMvc   //  MockMvc 생성 및 자동 구성
class TestControllerTest {
}