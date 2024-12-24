package me.ahngeunsu.springbootdeveloper.dto;

import lombok.Getter;
import me.ahngeunsu.springbootdeveloper.domain.Article;

@Getter
public class ArticleResponse {

    private final String title;
    private final String content;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
    /*
        글은 제목과 내용 구성이므로 해당 필드를 가지는 클래스를 만든 다음, 엔티티를 인수(arguement)로 받는 생성자를 추가함.

        02 단계 - controller 디렉토리에 있는 BlogApiController.java 파일을 열어 전체 글을 조회한 뒤 반환하는 findAllArticles() 메서드 추가
        BlogApiController.java로 가세요.
     */
}
