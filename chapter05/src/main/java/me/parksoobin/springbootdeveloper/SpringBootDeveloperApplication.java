package me.parksoobin.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
5장. DB 조작이 편해지는 'ORM'
    1. DB (DataBase) 란?
        데이터를 효율적으로 관리하고 꺼내볼 수 있는 곳

        DBMS(DataBase Management System) : 기본적으로 데이터베이스는 많은 사람이 공유할 수 있어야 하고,
                                            동시 접근이 가능해야하는 등 많은 요구 사항이 존재함.
                                            이를 만족시키면서도 효율적으로 데이터베이스를 관리하는 체계가 DBMS
                                            대부분 개발자들이 편하게 DB 라고 이야기하는 MySQL , Oracle, DBeaver 과 같은 것들은
                                            DB 가 아니라 DBMS 로 간주됨.

        관계형 DBMS
            Relational DBMS 를 줄여서 RDBMS 라고 부름. 테이블 형태로 이루어진 데이터 저장소
            RDBMS 는 테이블 ('표' 를 의미) 형태로 이루어진 데이터 저장소로 예를 들어 이하와 같은 회원 테이블이 있다고 가정할 때

             1열           2열           3열
            +----------------------------------+
            | ID         | 이메일       | 나이  | - header / column
            ------------------------------------
            | 1          | a@test.com  | 10    | - 1행
            | 2          | b@test.com  | 20    | - 2행
            | 3          | c@test.com  | 30    | - 3행
            +----------------------------------+
            각 행은 고유의 키 (PK) , 즉 ID 를 가지고 있고 이메일, 나이 와 같은 회원과 관련된 값들이 들어감.
            (JAVA 를 기준으로는 User 클래스의 클래스 인스턴스를 생각하면됨.)

            기본키(PK) :
            Prime Key

        H2, MySQL
            해당 수업에서 사용할 RDBMS 는 H2, MySQL
            H2 - 자바로 작성되어 있는 RDBMS : 스프링 부트가 지원하는 인메모리 관계형 DB
                 데이터를 다른 공간에 따로 보관하는 것이 아니라 애플리케이션 자체 내부에 데이터를 저장하는 것이 특징
                 그래서 애플리케이션을 재실행 시 데이터는 초기화됨 (서버 재실행을 기준으로)
                 간편하게 사용하기 좋아서 테스트용도로 자주 사용됨.
                 실제 서비스에서는 실행 안함
            MySQL - 실제 서비스로 올릴 때 사용 할 RDBMS -> 추후 수업 예정

    필수 용어
        1) 테이블 : DB 에서 데이터를 구성하기 위한 가장 기본적인 단위 행과 열로 구성되며
                    '행'은 여러 속성으로 이루어져있음

        2) 행 (row) : 테이블의 구성 요소 중 하나이며 테이블의 가로로 배열된 데이터의 집합을 의미
                      행은 반드시 고유한 식별자인 기본키(PK) 를 가짐. 행을 레코드(record)라고 부르기도 함.

        3) 열 (column) : 테이블의 구성 요소 중 하나이며, 행에 저장되는 유형의 데이터
                         예를 들어 회원 테이블이 있다고 할 때, 열은 각 요소에 대한 속성을 나타내며 무결성을 보장함. (JAVA 에서는 필드)
                         이상의 표를 기준으로 할 때에는 이메일은 문자열일 것, 나이는 숫자 유형을 나타냄
                         이메일 열에 숫자가 들어가거나 나이 열에 문자열이 들어갈 수 없기 때문에 데이터에 대한 무결성을 보장

        4) 기본키 (Primary Key) : 행을 구분할 수 있는 식별자. 이 값은 테이블에서 유일해야하며, '중복을 허용하지 않음'.
                                 보통 데이터를 수정하거나 삭제하고 조회할 때 사용되며, 다른 테이블과 관계를 맺어 데이터를 가져올 수 있음.
                                 또한 기본키의 값은 수정되어서는 절대 안되며 유효한 값이어야함. 즉 NULL 이 될 수 있음.
                                    -> 이전 수업에서 nullable = false

        5) 쿼리 (query) : DB 에서 데이터를 조회하거나 삭제, 생성, 수정과 같은 처리를 하기 위해서 사용하는 명령문
                         SQL 이라는 DB 전용 언어를 사용하여 작성함.
                         SQL (Structured Query Language) - 구조화 된 질의 언어

     2. ORM
        Object-Relational Mapping 이라고 하며 자바의 객체와 DB 를 연결하는 프로그래밍 기법
            예를 들어 DB 에 age, name 컬럼에 20, 홍길동 이라는 값이 있다고 가정했을 때, 이것을 자바에서 사용하려면
            SQL 을 이용하여 데이터를 꺼내 사용하지만, ORM 이 있다면 DB 에서 값을 마치 객체처럼 사용할 수 있음
            SQL 에 어려움을 겪고 있다 하더라도 자바 언어로만 DB 에 접근해서 원하는 값(데이터) 를 받아올 수 있는 방식
            즉, 객체와 DB 를 연결해 자바 언어로만 DB 를 다룰 수 있도록 하는 도구를 ORM 이라고 함.

        장점 :
            1) SQL 을 직접 작성하지 않고 사용하는 언어 (여기서는 JAVA) 로 데이터베이스에 접근 가능
            2) 객체지향적으로 코드를 작성할 수 있기 때문에 비즈니스 로직에만 집중 가능
            3) DBMS 가 추상화 되어있기 때문에 MySQL 에서 PostgreSQL 로 전환을 하더라도 추가로 드는 작업이 거의 없음.
            4) 매핑하는 정보가 명확하기 때문에 (DB 값과 인스턴스 변수의 값), ERD 에 대한 의존도를 낮출 수 있고 유지보수하기 편함

        단점 :
            1) 프로젝트의 복잡성이 커질 수록 난이도가 증가함.
            2) 복잡하고 무거운 쿼리는 ORM 으로 해결이 불가능한 경우가 있음 (JOIN 이 여러 번 들어가거나 / 서브 쿼리가 복잡 한 경우)

 */

@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class, args);
    }
}
