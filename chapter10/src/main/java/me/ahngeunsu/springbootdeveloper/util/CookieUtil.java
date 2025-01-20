package me.ahngeunsu.springbootdeveloper.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

public class CookieUtil {
    //  요청값 (이름, 값, 만료 기간)을 바탕으로 쿠키 추가
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge){
        //  import jakarta.servlet.http.Cookie;
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
        }

    // 쿠키의 이름을 입력 받아서 쿠키 삭제
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name){
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return;     // return; 메서드 종료
        }
        for (Cookie cookie : cookies) {
            if(name.equals(cookie.getName())) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }
    /*
        deleteCookie() : 쿠키 이름을 입력 받아서 쿠키를 삭제
            -> 실제로 삭제하는 방법은 없으므로 파라미터로 넘어온 키의 쿠키를
               빈 값으로 바꾼 후 만료 시간을 0 으로 설정하여 쿠키가 재생성되자마자 만료 처리를 함으로써 구성

             20250120 에 객체를 직렬화 / 역직렬화하는 메서드 구현 예정

             객체를 직렬화해서 쿠키의 값으로 변환
     */

    public static String serialize(Object obj){
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj));

    }

    //  쿠키를 역직렬화해서 객체로 변환
    public static <T> T deserialize(Cookie cookie, Class<T> cls){
        return cls.cast(
                SerializationUtils.deserialize(
                        Base64.getDecoder().decode(cookie.getValue())
                )
        );
    }
}
/*
    사용자 정보를 조회해 users 테이블에 사용자 정보가 있다면 리소스 서버(OAuth)에서 제공해주는 이름을 업데이트함

    사용자 정보가 없다면 새 사용자를 생성해서 DB 에 저장할 수 있도록 함.
    먼저 domain 의 User.java 수정
 */
