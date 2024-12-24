package me.ahngeunsu.springbootdeveloper.repository;

import me.ahngeunsu.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
/*
    이상의 코드로 JpaRepository 클래스를 상속 받을 때, 엔티티 Article과 PK 타입 Long을 인수로
    넣어주면, 해당 리포지토리를 사용할 때 JpaRepository에서 제공하는 여러 메서드들을 사용 가능

    3. 블로그 글 작성을 위한 API 구현하기
        엔티티 구성이 끝났으니 API를 하나씩 구현할 예정입니다. 구현 과정은 서비스 클래스에서 메서드를 구현하고,
        컨트롤러에서 사용할 메서드를 구현한 다음, API를 실제로 테스트할 예정입니다.

                   요청                    save()                   save()
        클라이언트 <----> 2. 컨트롤러 <-------------------> 1. 서비스 <------> 리포지토리
                   응답  (BlogController.java)          (BlogService.java)  (BlogRepository.java)
                   POST
               /api/articles

       서비스 메서드 코드 작성하기
           먼저 블로그에 글을 추가하는 코드를 서비스 계층에 작성할 예정입니다. 서비스 계층에서 요청을 받을 객체인 AddArticleRequest
           객체를 생성하고, BlogService 클래스를 생성한 다음에 블로그 글 추가 메서드인 save()를 구현할 예정입니다.

           01 단계 - springbootdeveloper 패키지에 dto 패키지를 생성한 다음, dto 패키지를 컨트롤러에서 요청한 본문을 받을 객체인
           AddArticleRequest.java 파일을 생성합니다.

           DTO(Data Transfer Object) : 계층끼리 데이터를 교환하기 위해 사용하는 객체.
           DAO는 데이터베이스와 연결되고 데이터를 조회하고 수정하는 데 사용되는 객체라 비교가 필요합니다.
           DAO의 경우에는 데이터 수정 관련된 로직이 포함되지만 DTO는 단순하게 데이터를 옮기기 위해 사용하는 전달자 역할을 하는 객체이기
           때문에 별도의 비지니스 로직을 포함하지 않습니다.

 */
