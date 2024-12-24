package me.ahngeunsu.springbootdeveloper.service;
/*
    서비스 메서드 코드 작성하기 02 단계 중입니다.
 */
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.ahngeunsu.springbootdeveloper.domain.Article;
import me.ahngeunsu.springbootdeveloper.dto.AddArticleRequest;
import me.ahngeunsu.springbootdeveloper.dto.UpdateArticleRequest;
import me.ahngeunsu.springbootdeveloper.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor    // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service                    // 빈으로 등록
public class BlogService {

    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }

    /*
        @Service 애너테이션은 해당 클래스를 빈으로 서블릿 컨테이너에 등록해줍니다.
        save() 메서드는 JpaRepository에서 지원하는 저장 메서드 save()로,
        AddArticleRequest 클래스에 저장된 값들을 article 데이터베이스에 저장합니다.

        컨트롤러 메서드 코드 작성하기
            이제 URL에 매핑하기 위한 컨트롤러 메서드를 추가합니다. 컨트롤러 메서드 구현 학습한 적 있습니다.
            컨트롤러 메서드에는 URL 매핑 애너테이션 @GetMapping, @PostMapping, @PutMapping, @DeleteMapping 등을
            사용할 수 있습니다. 이름에서 볼 수 있듯이 각 메서드는 HTTP 메서드에 대응합니다.
            여기에서는 /api/articles에 POST 요청이 오면 @PostMApping을 이용해 요청을 매핑한 뒤,
            블로그 글을 생성하는 BlogService의 save() 메서드를 호출한 뒤, 생성된 블로그 글을 반환하는 작업을 할
            addArticle() 메서드를 작성할 예정입니다.

            01 단계 - springbootdelveloper 패키지에 controller 패키지를 생성한 뒤, BlogApiController.java 파일 생성합니다.

     */
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    /*
        JPA 지원 메서드인 findAll()을 호출해 article 테이블에 저장되어 있는 모든 데이터를 조회합니다. 이제 요청을 받아 서비스에 전달하는
        컨트롤러를 만들겠습니다.

        컨트롤러 메서드 코드 작성하기
        /api/articles GET 요청이 오면 글 목록을 조회할 findAllArticles() 메서드를 작성할 예정. 이 메서드는 전체 글 목록을 조회하고 응답하는 역할

            01 단계 - 응답을 위한 DTO를 먼저 작성함. dto 디렉토리에 ArticleResponse.java 파일을 생성.
     */

    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }
    /*
        여기서 구현한 findById() 메서드는 JPA에서 제공하는 findById() 메서드를 사용해 ID를 받아 엔티티를 조회하고, 없으면
        IllegalArguemntException 예외를 발생합니다.

        컨트롤러 메서드 코드 작성하기

            01 단계 - api/articles/{id} GET 요청이 오며 블로그 글을 조회하기 위해 매핑할 findArticle() 메서드 작성.
                BlogApiController.java 파일을 열어 코드를 작성합니다.
     */

    public void delete(long id) {
        blogRepository.deleteById(id);
    }
    /*
        컨트롤러 메서드 코드 작성하기
            01 단계 - /api/articles/{id} DELETE 요청이 오면 글을 삭제하기 위한 findArticles() 메서드를 작성합니다.

            BlogApiController.java로 이동하세요.
     */

    @Transactional  // 트랜잭션 메서드
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }

    /*
        트랜잭션 - 데이터베이스에서 데이터를 바꾸기 위한 작업 단위를 말합니다.

            예를 들어 계좌 이체를 할 때 이런 과정을 거친다고 가정합시다.
                1) A 계좌에서 출금
                2) B 계좌에 입금
            그런데, 1)은 성공했는데 도중에 2)가 실패하면 어떻게 될까요? 고객 입장에서는 출금은 됐는데 입금이 안된 상황이 발생합니다.
            이런 상황이 발생하지 않으려면 출금과 입금을 하나의 작업 단위로 묶어서, 즉 트랜잭션으로 묶어서 두 작업을 한 단위로 실행하면 됩니다.
            만약 중간에 실패한다면 트랜잭션의 처음 상태로 모두 되돌리면 되는 것이죠.

        컨트롤러 메서드 코드 작성하기

            01 단계 - /api/articles/{id} PUT 요청이 오면 글을 수정하기 위한 updateArticle() 메서드를 작성하겠습니다.
                BlogApiController.java 파일을 열어 수정해주세요.

                BlogApiController.java 파일로 이동합니다.
     */
}
