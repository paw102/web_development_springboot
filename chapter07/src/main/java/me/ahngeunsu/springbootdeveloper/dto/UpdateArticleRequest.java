package me.ahngeunsu.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateArticleRequest {
    private String title;
    private String content;

    /*
        03 단계 - DTO가 완성되었으니 BlogService.java 파일을 열어 리포지토리를 사용해 글을 수정하는 update() 메서드를 추가합니다.

            BlogService.java로 이동하세요.
     */
}
