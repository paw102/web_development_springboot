package me.ahngeunsu.springbootdeveloper.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import me.ahngeunsu.springbootdeveloper.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {
    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt){
        Date now = new Date();
        // 빨간줄이 뜨는 상태는 아직 정의하지 않았기 때문임. -> 바로 밑에 강의 할 예정
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    // 1. JWT 토큰 생성 메서드
    private String makeToken(Date expiry, User user) {
        Date now = new Date();
        return Jwts.builder()           // import io.jsonwebtoken.Jwts;
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)   // 헤더 typ : JWT
                // 내용 iss : maybeags@gmail.com(jwtProperties 에서 설정함)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)               // 내용 iat : 현재 시간
                .setExpiration(expiry)          // 내용 exp : expiry 멤버 변수값
                .setSubject(user.getEmail())    // 내용 sub : 유저 이메일
                .claim("id",user.getId())    // 클레임 id : 유저 ID
                // 서명 : 비밀값과 함께 해시값을 HS256 방식으로 암호화
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    // 2. JWT 토큰 유효성 검증 메서드
    public boolean validToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())    // 비밀값으로 복호화
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 3. 토큰 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token){
        //  import org.springframework.security.core.Authentication;
        //  getClaims 최초 상황 시에 아직 정의 안함
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities),
                token, authorities);
    }

    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    private Claims getClaims(String token){
        return Jwts.parser()        // 클레임 조회
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}
/*
    1.  makeToken():
        토큰을 생성하는 메서드 argument 로 만료 시간, 유저 정보를 받음
        set 계열 메서드들을 통해 여러 값을 지정했는데,
        header 에는 typ, 내용에 iss, iat, exp, sub
        claim 에는 유저 id 지정
        토큰을 만들 때 properties 파일에 선언해둔 비밀 값과 함께 HS256 방식으로 암호화

    2.  validToken():
        토큰이 유효한 지 검증하는 메서드 properties 파일에 선언한 비밀값과 함께 토큰 복호화
        만약 복호화 과정에서 에러가 발생하면 유효하지 않은 토큰이므로 return false
        문제 없으면 return true

    3. getAuthentication():
        토큰을 받아 인증 정보를 담은 객체 Authentication 을 반환하는 메서드
        properties 파일에 저장한 비밀 값으로 토큰을 복호화 한 뒤 클레임을 가져오는 private 메서드
        getClaims() 를 호출, 클레임 정보를 반환받아 사용자 이메일이 들어있는 토큰 제목 sub 와
        토큰 기반으로 인증 정보를 생성

        이때 UserPasswordAuthenticationToken 의 첫 argument 로 들어가는 User 는 프로젝트에서 만든 클래스가 아니라
        스프링 시큐리티에서 제공하는 객체인 User 클래스를 import 해와야하기 때문에 경로를 강제로 설정을 함

    4.  getUserId():
        토큰 기반으로 사용자 ID 를 가져오는 메서드 properties 파일에서 저장한 비밀 값으로 토큰을 복사 한 후
        다음 클레임이 가져오는 private getClaim() 를 호출
        클레임 정보를 반환받고 클레임 id 키로 저장된 값을 가져와 반환

        리프레시 토큰 구현하기
            리프레시 토큰은 데이터베이스에 저장하는 정보이므로 엔티티와 리포터지토리를 추가해야 함.
            만들 엔티티와 매핑되는 테이블 구조는

           +--------------------------------------------------------------------------------+
           |    컬럼명         |   자료형         |    null 허용  |    키     |   설명
           ---------------------------------------------------------------------------------+
           | id               |   BIGINT        |       N       |   기본키   | 일련번호, 기본키
           | user_id          |   BIGINT        |       N       |           | 유지 ID
           | refresh_token    |   VARCHAR(255)  |       N       |           | 토큰값
           +--------------------------------------------------------------------------------+
 */
