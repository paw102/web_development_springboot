package me.parksoobin.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
1. 사전 지식 : API 와 REST API
    API : 프로그램 간에 상호 작용하기 위한 매개체

    식당으로 알아보는 API
        내가 손님이라 생각하고 식당에 들어갔을 때 -> 점원에게 요리를 주문함(주방으로 직접 가서 말하는 게 아니라)
        그러고나서 점원은 주방에 가서 요리를 만들어달라고 요청함
             요   청       요   청
        손님 <------> 점원 <------> 주방
             응   답       응   답

        의 형태를 띄고 있는데, 여기서 손님은 클라이언트(client), 주방에서 요리하는 요리사를 서버 (server) 라고 생각하면됨.
        그리고 중간에 있는 점원을 API 라고 보면됨. -> '매게체' 라고 한 점 주목

        우리는 웹 사이트의 주소를 입력해서 '구글 메인 화면을 보여줘' 와 같이 요청한다면 API 는 이 요청을 받아서 서버에다가 전달함
        그러면 서버는 API 가 준 요청을 처리해 결과물들을 구성하여 이것을 다시 API 로 전달하고 API 는 최종 결과물을 브라우저에
        보내준 화면을 볼 수 있게됨.
            이처럼 API는 클라이언트의 요청을 서버에 잘 전달하고 서버의 결과물을 클라이언트에 잘 돌려주는 역할을 함.

            그렇다면 REST API 란?

        웹의 장점을 최대한 활용하는 형태라고 알려진 REST API
            Representational State Transfer 를 줄인 표현으로, 자원을 이름으로 구분해 자원의 상태를 주고 받는 API 형식
            URL 의 설계 방식에 해당

        특징
            REST API 는 서버 / 클라이언트 구조, 무상태, 캐시 처리 가능, 계층화, 일관성과 같은 특징

        장점 :
             URL 만 보고도 무슨 행동을 하는 API 인지 명확하게 알 수 있음
             무상태 특징으로 인해 클라이언트와 서버의 역할이 명확하게 분리됨
             HTTP 표준을 사용하는 모든 플랫폼에 사용 가능

        단점 :
            HTTP 매서드, 즉 GET, POST 와 같은 방식의 개수의 제한이 있음
            설계를 위해 공식적으로 제공되는 표준 규약이 없음.

        장단점을 고려했을 때, 주소와 HTTP 메서드만 보고 요청의 내용을 파악할 수 있다는 장점으로 REST 하게 디자인한 API 를 보고
        RESTful API 라고 부르기도 하는 편

        REST API 를 사용하는 방법
            규칙 1. URL 에는 동사를 쓰지 않고 자원을 표시한다
                *자원 : 가져오는 데이터를 의미, 예를 들어 학생 중에 id가 1인 학생의 정보를 가져오는 URL 은
                1) 예시 : /students/1
                2) 예시 : /get-student?student_id=1
            과 같은 방식으로 설계 할 수 있는데

            이 중 더 REST API 에 맞는 형식은 1) 예시에 해당함. 2) 예시의 경우 자원이 아닌 다른 표현을 섞어 사용했기 때문(get)
            그러면 동사를 사용해서 생기게 되는 추후의 문제점 예시 -> 데이터를 요청하는 URL 을 설계할 때
                A 개발자는 get, B 개발자는 show 를 쓰면 get-student, show-data 등으로 협의가 이루어지지 않은 설계 될 가능성 존재

            기능 / 행위 에 해당하지만 RESTful API 에서는 동사를 전혀 사용하지 않음


            규칙 2. 동사는 HTTP 메서드로
                HTTP 메서드 : 서버에 요청을 하는 방법을 나누는 것 -> POST, GET, PUT, DELETE
                    만들고, 읽고, 업데이트하고 삭제한다 Create / Read / Update / Delete 라 하여 CRUD 라고 함.

                1. 예를 들어 블로그에 글을 쓰는 설계를 한다고 가정했을 때

                    1) id가 1인 블로그 글을 '조회' 하는 API : GET/articles/1
                    2) 블로그 글을 '추가' 하는 API : POST/articles       -> 추가가 완료되기 전이라 DB 상 id 가 없음
                    3) 블로그 글을 '수정' 하는 API : POST/articles/1
                    4) 블로그 글을 '삭제' 하는 API : DELETE/articles/1

            * GET / POST 등은 URL 에 입력하는 값이 아니라 내부적으로 처리하는 방식을 미리 정하는 것으로
              실제로 HTTP 메서드는 내부에서 서로 다른 함수로 처리하는데 대놓고 적는 일은 잘 없음

              이외에도 '/' 는 계층 관계를 나타내는 데 사용하거나, 밑줄 대신 하이폰을 사용하거나 자원의 종류가
              컬렉션인지 도큐먼트인지에 따라 단수, 복수를 나누거나 하는 등의 규칙이 있지만 추후 설명 예정

                2. 블로그 개발을 위한 엔티티 구성
                  엔티티와 매핑되는 테이블 구조 ↓
                +-------------------------------------------------------------+
                | 컬럼명  | 자료형        | null 허용 |  키   |       설명        |
                +-------------------------------------------------------------+
                | id     | BIGINT       |     N     | 기본키 | 일련번호, 기본 키  |
                ---------------------------------------------------------------
                | title  | VARCHAR(255) |     N     |       | 게시물의 제목      |
                ---------------------------------------------------------------
                | content| VARCHAR(255) |     N     |       | 내용             |
                +--------------------------------------------------------------+
 */
@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class, args);
    }
}
