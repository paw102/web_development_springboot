package me.ahngeunsu.springbootdeveloper.dto;
// 서비스 메서드 코드 작성하기 01단계 중입니다.

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ahngeunsu.springbootdeveloper.domain.Article;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {

    private String title;
    private String content;

    public Article toEntity() { // 생성자를 이용해 객체 생성
        return Article.builder()
                .title(title)
                .content(content)
                .build();
    }

    /*
        toEntity()는 빌더 패턴을 사용해 DTO를 엔티티로 만들어주는 메서드입니다. 이 메서드는 추후
        블로그에 글을 추가할 때 저장할 엔티티로 변환하는 용도로 사용합니다.

        02 단계 - springbootdeveloper 패키지에 service 패키지를 생성한 뒤, service 패키지에서
            BlogService.java를 생성해 BlogService 클래스를 구현합니다.
     */
}
