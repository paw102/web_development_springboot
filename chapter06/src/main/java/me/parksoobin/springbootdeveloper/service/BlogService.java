package me.parksoobin.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import me.parksoobin.springbootdeveloper.domain.Article;
import me.parksoobin.springbootdeveloper.dto.AddArticleRequest;
import me.parksoobin.springbootdeveloper.repository.BlogRepository;

@RequiredArgsConstructor        // final 이 붙거나 @NotNull 이 붙은 필드의 생성자 추가
@Service                       // 빈으로 등록
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request){
        return blogRepository.save(request.toEntity());
    }
    /*
        @Service : 해당 클래스를 빈으로 서블릿 컨테이너에 등록
        save() : JpaRepository 에서 지원하는 저장 메서드 save() 로,
            AddArticleRequest 클래스에 저장된 값들을 article DB 에 저장함.

        -> 글을 생성하는 서비스 계층에서의 코드 작성 완료

        -> 컨트롤러 메서드 코드 작성
            URL 에 매핑하기 위한 컨트롤러 메서드 추가 -> 컨트롤러 메서드 구현 자체는 한 적 있는데, 컨트롤러 메서드에는 URL
            매핑 애너테이션인
            @PostMapping
            @GetMapping
            @PutMapping
            @DeleteMapping 등을 사용 할 수 있음
            이름에서 유추 할 수 있듯이 각 애너테이션들은 HTTP 와 대응함

            api/articles 에 POST 요청이 들어오면 @PostMapping 을 이용해 요청을 매핑 한 후
            블로그 글을 생성하는 BlogService 의 save() 메서드를 호출
            -> 호출된 블로그 글을 반환하는 작업을 하는 addArticle() 메서드를 작성 할 예정

            지시 사항
            service 와 동일한 라인에 controller 패키지 생성 후 BlogApiController.java 생성
     */
}
