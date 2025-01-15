package me.ahngeunsu.springbootdeveloper.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt")     // 자바 클래스에 프로퍼티 값을 가져와 사용하는 애너테이션
public class JwtProperties {
    private String issuer;
    private String secretKey;

    /*
        이렇게 하면 issuer 를 필드에서 application.yml 에서 설정한 jwt.issuer 값이,
        secretKey 에서는 jwt.secret_key 값이 매핑됨

        동일한 패키지 내에 TokenProvider.java 를 생성
     */
}
