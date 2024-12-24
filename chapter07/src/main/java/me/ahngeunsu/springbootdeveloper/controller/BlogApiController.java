package me.ahngeunsu.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ahngeunsu.springbootdeveloper.domain.Article;
import me.ahngeunsu.springbootdeveloper.dto.AddArticleRequest;
import me.ahngeunsu.springbootdeveloper.dto.ArticleResponse;
import me.ahngeunsu.springbootdeveloper.dto.UpdateArticleRequest;
import me.ahngeunsu.springbootdeveloper.service.BlogService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController // HTTP Reponse Body에 객체 데이터를 JSOM 형식으로 반환하는 컨트롤러
public class BlogApiController {

    private final BlogService blogService;

    // HTTP 메서드가 POST 일 때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/articles")
    // @RequestBody로 요청 본문 값 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);

        // 요청한 자원이 성공적으로 생성되었으며 저장된 블로그 글 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }
    /*
        @RestController 애너테이션을 클래스에 붙이면 HTTP 응답으로 객체 데이터를 JSON 형식으로 반환합니다.
        @PostMapping() 애너테이션은 HTTP 메서드가 POST일 때 요청 받은 URL 과 동일한 메서드와 매핑합니다.
        지금의 경우 /api/articles는 addArticles() 메서드에 매핑합니다.
        @RequesteBody 애너테이션은 HTTP 요청을 할 때, 응답에 해당하는 값을 @RequestBody 애너테이션을이 붙은
        대상 객체인 AddArticleRequest에 매핑합니다.
        ResponseEntity.status().body()는 응답 코드로 201, 즉 Created를 응답하고 테이블에 저장된 객체를 반환합니다.

        꼭 알아두면 좋을 응답 코드

        200 OK : 요청이 성공적으로 수행되었음
        201 Created : 요청이 성공적으로 수행되었고, 새로운 리소스가 생성되었음
        400 Bad Request : 요청 값이 잘못되어 요청에 실패했음
        403 Forbidden : 권한이 없어 요청에 실패했음
        404 Not Found : 요청 값으로 찾은 리소스가 없어 요청에 실패했음
        500 Internal Server Error : 서버 상에 문제가 있어 요청에 실패했음

        이까지 하고 API가 완성됐으니 API가 잘 동작하는지 테스트 할겁니다.

        API 실행 테스트하기
            실제 데이터를 확인하기 위해 H2 콘솔을 활성화해야 합니다. 현재는 H2 콘솔이 비활성화 돼있으므로
            속성 파일을 수정해줘야 합니다.

            01 단계 - resource 폴더에 application.yml 파일이 있습니다. 파일을 열어 코드를 추가합니다.

            02 단계 - 스프링부트 서버를 실행하세요. 이후 포스트맨 실행합니다.
            HTTP 메서드는 POST로, URL에는 http://localhost:8080/api/articles, BODY는 raw->JSON으로 변경한 다음
            요청 창에
            {
                "title": "제목",
                "content": "내용"
            }
            으로 작성하고, Send 눌러 요청을 보내세요. Body에 pretty모드로 결과를 보여줄 겁니다.
            실제 값이 스프링 부트 서버에 저장된 것입니다. 해당 과정이 HTTP 메서드 POST로 서버에 요청해 값을 저장하는
            과정에 해당합니다.

            -> 일단 여기까지 성공

            응값 값을 보면 데이터가 잘 저장된 것 확인할 수 있음. 실제로도 그런지 확인해봐야하는데, 즉
            H2 데이터베이스에 잘 저장됐는지 확인해봅니다.

            03 단계 - 웹 브라우저에서 localhost:8080/h2-console에 접속해봅니다. 스프링 부트 서버는
                켠 상태를 유지해야 합니다.

                Driver Class : org.h2.Driver
                JDBC URL : jdbc:h2:mem:testdb
                User Name : sa 로 입력하고 Connect 눌러서 로그인합니다. 이렇게 하면 스프링 부트 서버 안에
                내장돼있는 H2 데이터베이스에 접속하고 데이터를 확인할 수 있게 됩니다.

            04 단계 - SQL statement:의 입력 창에 SELECT * FROM ARTICLE을 입력한 뒤 Run을 눌러 쿼리를 실행합니다.
            이렇게 하면 H2 데이터베이스에 저장된 데이터를 확인할 수 있습니다. 그리고 왼쪽을 보면 ARTICLE이라는 테이블도 보입니다.
            애플리케이션을 실행하면 자동으로 생성한 엔티티 내용을 바탕으로 테이블이 생성되고, 우리가 요청한 POST 요청에 의해
            실제로 데이터가 저장된 것입니다.

        반복 작업을 줄여 줄 테스트 코드 작성하기
            H2 콘솔에 접속해 쿼리를 입력해 데이터가 저장되는지, 그것이 실제로 들어있는지 확인했습니다.
            앞으로 개발을 하면서 이런 테스트 과정을 계속 거쳐야 할 텐데, 매번 이런 방식으로 테스트 하려면 불편합니다.
            테스트 코드 작성법을 확인하겠습니다.

                01 단계 - BlogApiController 클래스에 Alt+enter 누르고 create Test 누르면 테스트 생성 창이 열립니다.
                기본 값을 그대로 두고 테스트 코드 파일을 생성하세요.

     */

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }
    /*
        /api/articles GET 요청이 들어오면 글 전체를 조회하는 findAll() 메서드를 호출한 다음 응답용 객체인 ArticlesResponse로 파싱해 body에 담아
        클라이언트에게 전송합니다. 이 코드에는 스트림을 적용하였습니다.

        * 스트림 - 여러 데이터가 모여 있는 컬렉션을 간편하기 처리하기 위해서 자바 8에서 추가된 기능입니다.

        실행 테스트 하기
            01 단계 - 테스트를 쉽게 하기 위해 data.sql 파일을 생성합니다. resource 디렉토리에 data.sql 파일을 만드세요.

            02 단계 - 포스트맨을 열고 HTTP 메서드는 GET으로, Params 탭으로 변경한 다음 URL은 http://localhost:8080/api/articles라고 입력해
                send를 누르세요.

                postman에 제이슨 형태로 출력되면 성공.

            테스트 코드 작성하기
                01 단계 - 글 조회 테스트 역시 편의를 위해 테스트 코드 작성을 하겠습니다. 다음과 같은 given-when-then 패턴으로 코드를 작성하겠습니다.
                코드는 BlogApiControllerTest.java에 이어 작성해주세요.
     */

    @GetMapping("/api/articles/{id}")
    // URL 경로에서 값 추출
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) { // URL에서 {id}에 해당하는 값이 id로 들어옴
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }
    /*
        @PathVariable 애너테이션은 URL에서 값을 가져오는 애너테이션. 이 애너테이션이 붙은 메서드의 동작 원래는
            /api/articles/3 GET 요청을 받으면 id에 3이 들어옵니다. 그리고 이 값은 앞서 만든 서비스 클래스의 findById()
            메서드로 넘어가 3번 블로그 글을 찾습니다. 그 글을 찾으면 3번 글의 정보를 body에 담아 웹 브라우저로 전송합니다.

        테스트 코드 작성하기

            01 단계 - 테스트 코드를 작성해 글 조회가 잘 되는지 확인하겠습니다.
                given - 블로그 글을 저장합니다.
                when - 저장한 블로그 글의 id 값으로 API를 호출합니다.
                then - 응답 코드가 200 OK이고, 반환 받은 content와 title이 저장된 값과 같은지 확인합니다.

                BlogApiControllerTest.java 파일로 넘어가세요.
     */

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }
    /*
        /api/articles/{id} DELETE 요청이 오면 {id}에 해당하는 값이 @PathVariable 애너테이션을 통해 들어옵니다.

        실행 테스트 하기

            01 단계 - 로직이 모두 완성되었으니 실제로 테스트하는 방법을 알아보겠습니다. 포스트맨에서 [DELETE]로 HTTP 메서드를 설정,
                URL에는 http://localhost:8080/api/articles/1을 입력하세요. 후에 send를 누릅니다.

            02 단계 - 그 이후에 앞서 만들어둔 블로그 글을 조회하는 API에 요청을 보내보겠습니다. 포스트맨에서 HTTP 메서드를 GET으로 설정,
                http://localhost:8080/api/articles 요청을 보냅니다.

                ID가 1인 글은 삭제되었다면, 2개의 글만 보일겁니다.

        테스트 코드 작성하기

            01 단계 - 이제 테스트 코드를 작성하며 삭제 API 구현을 마무리 합니다.
            given - 블로그 글을 저장합니다.
            when - 저장한 블로그 글의 id값으로 삭제 API를 호출합니다.
            then - 응답 코드가 200 OK이고, 블로그 글 리스트를 전체 조회해 조회한 배열 크기가 0인지 확인합니다.

            BlogApiControllerTest.java로 이동합니다.
     */

    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request) {
        Article updateArticle = blogService.update(id, request);

        return ResponseEntity.ok().body(updateArticle);
    }
    /*
        /api/articles/{id} PUT 요청이 들어오면 Request Body 정보가 request로 넘어옵니다. 그리고 다시 서비스 클래스의 update() 메서드에
        id와 request를 넘겨줍니다. 응답 값은 boyd에 담아 전송합니다.

        실행 테스트하기

            01 단계 - 포스트맨에서 HTTP 메서드는 PUT으로 설정, 그리고 URL은 http://localhost:8080/api/articles/1입니다.
                수정 내용을 입력하려면 [Body]을 사용해야 합니다. 또, [raw], [JSON]으로 설정한 다음 수정할 내용을 JSON형태로
                {
                    "title":"어쩌고",
                    "content":"저쩌고"
                }
                형식으로 입력합니다. 입력 한 후 [send] 눌러 요청을 날려보시면 됩니다.

            02 단계 - 수정된 글은 글 전체 조회 API로 확인하면 됩니다. 전체 글을 조회해보면 ID가 1인 글이 수정되었음을 알 수 있습니다.
                어떻게 확인한다? GET 요청으로 확인한다.

        테스트 코드 작성하기

            01 단계 - 여기서도 수정 테스트를 위한 코드를 작성하겠습니다. BlogApiControllerTest.java 파일에 코드를 추가합니다.

     */


}
