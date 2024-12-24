package springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
    1. 스프링 부트 3 구조 살펴보기
        각 계층이 양 옆의 계층과 통신하는 구조
        계층 : 각자의 역할과 책임이 어떤 소프트웨어의 구성 요소
            -> 각 계층은 소통할 수는 있지만 다른 계층애 직접 간섭하거나 영향을 미치진 않음

        ex) 카페와 빵집
            카페에서는 커피를 팔고, 빵집에서는 빵을 파는데, 필요한 경우 서로 협업 관계를 맺어서
            어떤 손님이 커피를 사면, 빵을 할인해 '줄 수는' 있음 -> 계층 간 소통
            하지만 빵집 점원이 커피를 서빙할 수는 없음.

       스프링 부트에서의 계층
        1) 프레젠테이션 (presentation)
            HTTP 요청을 받고 이 요청을 비즈니스 계층으로 전송하는 역할 -> Controller 가 프레젠테이션 계층 역할
        2) 비즈니스 (business)
            모든 비즈니스 관련 로직을 처리
                * 비즈니스 로직 : 서비스를 만들기 위한 로직을 의미하며, 웹 사이트에서 벌어지는 모든 작업
                                -> 주문 서비스라고 한다면 주문 개수, 가격 등의 데이털르 처리하기 위한 로직, 주문 처리를 하다가
                                    발생하는 예외 처리 로직, 주문을 받거나 취소하는 등 프로세스를 구현하기 위한 로직 등을 의미

        3) 퍼시스턴스 (persistence)
            모든 DB 관련 로직을 처리 이 과정에서 DB에 접근하는
                DAO(추후 수업 예정) 객체를 사용 할 '수'도 있음
                    * DBO - DB 계층과 상호작용하기 위한 객체라고 이해하면 편함.
                    일단은 Repository 가 퍼시스턴스 계층의 역할을 함.

        계층은 개녕믜 영역이고 Controller, Service, Repository 는 실제 구현을 위한 영역
            -> 추후 수업 예정

        스프링 부트 프로젝트 디렉토리 ㄱ구성
            : 스프링 부트에는 정해진 구조가 없지만 추천 프로젝트 구조가 있고 이를 많은 개발자들이 따르도록
                수업 시에도 해당 구조를 따라 개발할 예정.

                main : 실제 코드를 작성하는 공간. 프로젝트 실행에 필요한 소스 코드나 리소스 파일은 모두
                        이 폴더 안에 들어있음 (프로젝트 생성 시 자동 구현됨)

                test : 프로젝트 소스 코드를 테스트 할 목적의 코드나 리소스 파일이 들어있음
                        (원래는 자동생성되는데 어제 만들었을 때 안만들어지는 상호아도 있다보니 설정 따라 다른듯함)

                build.gradle : 빌드를 설정하는파일. 의존성이나 플러그인 설정과 같이 빌드에 필요한 설정을 할 때 사용

                setting.gradle : 빌드할 프로젝트의 정보를 설정하는 파일

                지시 사항
                    main 디렉토리 구성
                        : main 디렉토리 내에 java 와 resources 로 구성되어 있음. 여기에 아직 추가하지 못했던 스프링 부트
                            프로젝트의 구성요소 추가
                    1. HTML 과 같은 뷰 관련 파일 (chapter07 에서 수업 예정) 을 넣을 templates 디렉토리 추가
                        resources 우클릭 -> new directory -> templates
                    2. static 디렉토리, JS , CSS, 이미지와 같은 정적 파일을 넣는 용도로 사용 -> 생략
                    3. 스프링 부트 설정을 할 수 있는 application.yml 파일을 생성
                        -> 해당 파일은 스프링 부트 서버가 실행되면 자동으로 로딩되는 파일로,
                            DB 의 설정 저보, 로깅 설정 정보 등이 들어갈 수도 있고 직접 정의할 때 사용하기도 함.
                            앞으로 프로젝트를 진행하며 자주 사용할 예정
                            resource 우클 -> new file -> application.yml

        2. 스프링부트 3 프로젝트 발전시키기

            각 계층의 코드를 추가 할 예정 -> 이를 통해 계층이 무엇이고, 스프링 부트에서는 계층을 어떻게 나누는 지를 감을 잡아나갈 수 있게
            연습 예정

            지금은 의존성을 추가한 다음에 프레젠테이션, 비즈니스. 퍼시스턴스 계층 순서로 코드 추가

            *****의존성 주입 이후*****

            implementation : 프로젝트 고드가 컴파일 시점과 런타임에 모두 해당 라이브러리를 필요로 할 때 사용
            testImplementation : 프로젝트의 테스트 모드를 컴파일 하고 실행할 때만 필요한 의존성 설정,
                                 테스트 코드에서만 사용, 메인 애플리케이션에서는 사용하지 않음
            runtimeOnly : 런타임에만 필요한 의존성을 지정, 컴파일 시에는 필요하지 않지만 애플리케이션을 실행할 때 필요한 라이브러리 설정
            compileOnly : 컴파일 시에만 필요, 런타임에는 포함되지 않아여하는 의존성 지정
            annotationProcessor : 컴파일 시에만 애너테이션을 처리할 때 사용하는 도구의 의존성 지정

   지시 사항
    프레젠테이션, 서비스, 퍼시스턴스 계층 만드릭

        1. 프레젠테이션 계층에 속하는 컨트롤러 관련 코드를 작성 -> TestController 가 있으므로 생략
        2. 비즈니스 계층 코드 -> TestController 와 같은 위치에 TestService.java 생성
        3. 퍼시스턴스 계층 코드 -> 같은 위치에 Member.java 생성 -> 실제 DB 에 접근하는 코드 작성
        4. 매핑 작업에는 '인터페이스' 파일이 필요 -> MemberRepository 인터페이스를 같은 위치에 생성



    작동 확인하러가셈.

 */
@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class, args);
    }
}