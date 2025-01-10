package me.parksoobin.springbootdeveloper.dto;

import lombok.Getter;
import me.parksoobin.springbootdeveloper.domain.Article;

@Getter
public class ArticleListViewResponse {
    private final long id;
    private final String title;
    private final String content;

    public ArticleListViewResponse(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
/*
    다 작성한 분들 controller 패키지에 BlogViewController 파일 만들겠습니다.
 */
