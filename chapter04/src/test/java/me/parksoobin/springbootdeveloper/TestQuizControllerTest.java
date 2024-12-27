package me.parksoobin.springbootdeveloper;
/*
    Test 클래스를 만드는 방법

    1) 테스트하고자 하는 클래스 (main/java 내에 있는 클래스) 를 엽니다.
    2) public class 클래스명이 있는 곳에 클래스 명을 클릭
    3) alt + enter 누르면 팝업이 나옵니다
    4) create test 선택
    5) 저희 프로젝트 상으로는 JUnit5 로 고정되어 있습니다.
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // 테스트용 애너테이션 컨텍스트 생성
@AutoConfigureMockMvc   //  MockMvc 생성 및 자동 구성
class TestQuizControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @AfterEach  // 테스트 실행 후 실행하는 메서드
    public void cleanUp() {
        memberRepository.deleteAll();
    }

    @DisplayName("getAllMembers : 아티클 조회에 성공한다.")
    @Test
    public void getAllMembers() throws Exception {      //  throws Exception : 예외 처리함
        final String url = "/test";
        Member savedMember = memberRepository.save(new Member(1L, "홍길동"));


        // when
    final ResultActions result = mockMvc.perform(get(url)       // (1)
                .accept(MediaType.APPLICATION_JSON));           // (2)

        // then
        result.andExpect(status().isOk())                       //  (3)
                                                                //  (4)
                .andExpect(jsonPath("$[0].id").value(savedMember.getId()))
                .andExpect(jsonPath("$[0].name").value(savedMember.getName()));
    }
}
/*
    (1) : perform() 메서드는 요청을 전송하는 역할을 하는 메서드
        return 값으로 ResultActions 객체를 받음.
        ResultActions 객체는 반환값을 검증하고 확인하는 andExpect() 메서드를 제공함
        
    (2) : accept() 메서드는 요청을 보낼 때 무슨 타입으로 '응답을 받을 지' 결정하는 메서드
            주로 JSON 을 사용
            
    (3) : andExpect() 메서드는 응답을 검증. testController 에서 만든 API 는 응답으로 OK(200) 을 반환하므로 이에 해당하는
            메서드인 isOK()를 사용하여 응답 코드가 200(OK) 인지 확인 
            
    (4) : jsonpath("$[0].{필드명}) 은 JSON 응답값의 값 (value) 을 가져오는 역할을 하는 메서드
            0(인덱스) 번 째 배열에 들어가 있는 객체의 id, name 의 값을 가져오고 저장된 값과 같은지 확인
            (memberRepository.savedMember.getId()등을 이용해서)
            
             
 */
/*
    @SpringBootTest :
        애플리케이션 클래스에 추가하는 애너테이션인 @SpringBootApplication 이 있는 클래스를 찾고
        그 클래스에 포함돼 있는 빈을 찾은 다음, 테스트용 애플리케이션 컨텍스트라는 것을 생성함.

    @AutoConfigureMockMvc :
        MockMvc 를 생성하고 자동으로 구성하는 애너테이션으로 이것은 애플리케이션을 서버에 배포하지 않아도
        테스트용 MVC 환경을 만들어 요청, 전송, 응답 기능을 제공하는 유틸리티 클래스
        즉 '컨트롤러를 테스트 할 때 사용되는 클래스'
 */