package me.ahngeunsu.springbootdeveloper.domain;
// 01 단계 진행 중입니다.

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity     // 엔티티로 지정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id     // id 필드를 기본키로 지정(jakarta입니다)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 즏ㅇ가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)   // 'title'이라는 not null 컬럼과 매핑
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder    // Builder 패턴으로 객체 생성
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 여기를 추가했습니다.

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /*
        02 단계 - 그런 다음 블로그 글 수정 요청을 받을 DTO를 작성해야 합니다. dto 디렉토리에 UpdateArticleRequest.java
            파일을 만들어 다음과 같이 작성하세요.

            UpdateArticleRequest.java  파일을 생성합니다.
     */

//    protected Article() {}      // 기본 생성자

//    // Getter 생성하세요
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getContent() {
//        return content;
//    }

    /*
        @Builder 애너테이션은 롬복에서 지원하는 애너테이션으로, 생성자 위에 입력하면 빌더 패턴 방식으로 객체를 생성 가능
        빌더 패턴을 사용하면 객체를 유연하고 직관적으로 생성할 수 있기 때문에 개발자들이 애용하는 디자인 패턴으로,
        어느 필드에 어떤 값이 들어가는지 명시적으로 파악할 수 있습니다.

        빌더 패턴 비교 예시입니다.

        빌더 패턴 미사용시
        New Article("abc", "def");

        빌더 패턴 사용시
        Article.builder()
            .title("abc")
            .content("def")
            .build();

        이상과 같이 기존의 생성자를 사용했을 때는 abc는 어느 필드에 들어가는지, def는 어느 필드에 들어가는지 알 수 없지만,
        빌더 패턴을 사용할 경우에는 title에 abc가 들어감을 확인할 수 있으므로 객체 생성 코드의 가독성이 높습니다.

            02 단계 - 이상의 코드에 롬복을 더 적용하여 코드가 더 깔끔하게 바뀌는지 확인하겠습니다.
                일단 getter들을 롬복으로 처리할 수 있습니다.
                또한 기본 생성자도 롬복으로 처리할 수 있습니다.

                @NoArgsConstructor를 이용하고 access 적용했습니다.

        이제 리포지토리 만들기로 들어갑니다.

        리포지토리 만들기
            01 단계 - springbootdeveloper 패키지에 repository 패키지를 만든 다음,
                BlogRepository.java 파일을 생성하여 BlogRepository 인터페이스를 만드세요.
     */


}
