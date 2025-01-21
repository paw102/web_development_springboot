package me.ahngeunsu;
/*
    1. 람다식 (Lamda Expression) 의 정의
        : Java8 에서 도입된 프로그래밍의 기능
        함수형 프로그래밍을 Java 에 도입하기 위한 핵심 기능 중 하나

        익명 함수를 생성하는 표현식으로 코드의 간결성 및 가독성으 높이는 데에 유용함

        형식 :
        (매개변수) -> {실행문}

        매개변수 : 람다식에서 처리 할 입력 값
        화살표(->) : 매개변수와 실행 코드를 구분
        실행문 : 람다식이 수행 할 작업

        예시 : 숫자를 두 배로 만드는 함수는 람다식을 사용하여 다음과 같이 작성 가능
        x -> x * 2          : x = x*2

        2. 람다식의 특징
            1) 간결성 : 익명 클래스 구현이나 기존 메서드의 정의 방식보다 짧고 간결함
                -> 클래스를 나눠서 일일이 method 구현 할 필요 X
            2) 함수형 인터페이스 : 함수형 인터페이스란 단 하나의 추상 메서드만 가지는 인터페이스로
                                대표적으로는 Runnable, Callable, Comparator 등이 있음
            3) 지연 실행 : 람다식은 실행될 때까지 평가되지 않으므로 코드의 지연 실행이 가능함
            4) 컨텍스트 의존성 : 람다식으 ㅣ타입은 함수형 인터페이스를 구현하는 곳에 따라 결정됨

        3. 기존 메서드 표기법과의 비교 및 대조
           main 확인 할 것

        4. 람다식 장단점
            장점 :
                1. 간결함
                2. 효율성 : 불필요한 익명 클래스 생성이 줄어들어 메모리 사용에 최적화
                3. 함수형 프로그래밍 지원 : Java 에서 객체지향에서 벗어나 함수형 프로그래밍을 구현할 수 있는 도구를 제공
                4. 코드 유지 보수성 : '간단한 동작을 위한 코드' 는 수정 및 유지 보수가 쉬움

            단점 :
                1. 복잡한 로직 구현의 어려움 : 긴 메서드를 구현하는 경우에는 비효율적임
                2. 디버깅의 어려움 : 익명 클래스에 비해 디버깅이 더 어려움
                    -> 오류 상황에서 람다식의 위치 파악이 어려움
                3. 함수형 인터페이스의 제한 : 함수형 인터페이스를 기반으로 동작하기 때문에 모든 경우에 사용 할 수 있는 것은 아님
 */

import java.util.Comparator;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        //  기존 익명 클래스 방식
        Comparator<Integer> comparator1 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };

        //  람다식 방식
        Comparator<Integer> comparator2 = (((o1, o2) -> o1 - o2));
        //  예시 1. 숫자를 제곱하는 람다식
        Function<Integer,Integer> square = x -> x * x;

        System.out.println("4의 제곱 : " +  square.apply(4));
        System.out.println("5의 제곱 : " +  square.apply(5));

        //  () -> {실행문} : 매개변수가 없는 경우 사용하는 사례
    }
}