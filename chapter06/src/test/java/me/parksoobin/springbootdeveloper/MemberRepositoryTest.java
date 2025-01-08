package me.parksoobin.springbootdeveloper;
/*
    MemberRepositoryTest 를 만들었지만 데이터 조회를 위해서 입력된 데이터가 필요하기 때문에 테스트용 데이터를 추가할 예정
    test -> resources -> insert-member-sql 생성
    작성 후 (혹은 확인 후에) 코드 의미 :
        src/main/resources 폴더 내에 있는 data.sql 파일을 자동 실행 못하게 하는 코드

    이후 MemberRepository.java 파일 코드 작성
 */

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Sql("/insert-members.sql")
    @Test
    void getAllMembers(){
        // when
        List<Member> members = memberRepository.findAll();
        //  memberRepository 는 상속받은 메서드명을 그대로 가져가기 때문에 우라가 직접 정의하는 영어가 아니라도 익숙해져야함.

        // then
        assertThat(members.size()).isEqualTo(3);
    }

    /*
        이상의 코드는 멤버 전체를 찾고, 그 리스트의 사이즈가 3인지를 확인하는 테스트

        이하의 코드는 id가 2인 멤버를 찾을 것임.
        ex)
        SELECT * FROM member WHERE id = 2;
        member 라는 테이블의 모든 col 을 조회하는데, id 가 2인 객체의 값들을 보여달라는 뜻임.
        FROM member,       *(aterisk : all), WHERE id = 2,        SELECT
     */

    @Sql("/insert-members.sql")
    @Test
    void getMemberById() {
        //  when
         Member member = memberRepository.findById(2L).get();       // 2L 에서 L의 의미 : Long

        //  then
        assertThat(member.getName()).isEqualTo("B");
    }

    /*
        이젠 id 가 아닌 name 을 기준으로 찾아봄
        id = 기본키, name 의 경우에는 column 이 있을 수도 없을 수도 있기 때문에 JPA 에서 기본으로 name 을 찾아주는 메서드가 없음.

        필수 column 이 아닌 것을 조건으로 검색을 하기 위해서는 MemberRepository.java 에서 메서드를 추가 할 필요가 있음.

        MemberRepository.java 로 이동

        MemberRepository 에 findByName 정의 후에 이하에 작성
     */

    @Sql("/insert-members.sql")
    @Test
    void getMemberByName(){     //  MemberRepository.java 에는 findByName() 이었다는 점에 주목
        //  when
        Member member = memberRepository.findByName("C").get();
        //  then
        assertThat(member.getId()).isEqualTo(3);
    }

    /*
            이상과 같이 MemberRepository.java 에 method 를 정의하는 것을 '쿼리 메서드' 라고함
            일반적인 경우 JPA 가 정해 준 메서드 이름 규칙을 따르면 쿼리문을 특별히 구현하지 않ㄷ아도
            메서드처럼 사용할 수 있음 (즉, 자바만으로도 컨트롤이 가능하다는 의미)

            이상의 메서드를 SQL 문으로 작성할 경우
            SELECT * FROM member WHERE name = 'C';

            지금까지 조회 관련 메서드 수업임
            전체 조회 -> findAll()
            아이디로 조회 -> findById()
            특정 컬럼으로 조회 (name) -> 쿼리 메서드 명명 규칙에 맞게 정의 후에 사용 (find / get 차이)

            추가 / 삭제 메서드

            INSERT INTO member (id, name) VALUES (1, 'A'); 라는 쿼리가 있다고 가정할 때
            JPA 에서는 save() 라는 메서드를 사용
     */

    @Test       //  @Sql 애너테이션을 달지 않음. 이 메서드에서 1, 'A' 에 해당하는 객체를 저장할거기 때문에
    void saveMember(){
        //  given
        Member member = new Member(1L,"A");
        //  when
        memberRepository.save(member);
        //  then
        assertThat(memberRepository.findById(1L).get().getName()).isEqualTo("A");
    }

    /*
        이상의 코드는 하나의 멤버 객체를 추가하는 메서드
        만약에 여러 Entity 를 저장하고 싶다면 saveAll() 메서드 사용 가능
     */

    @Test
    void saveMembers(){
        //  given - 다수의 객체를 저장 할 예정이므로 Collection 사용 예정 -> List
        List<Member> members = List.of(new Member(2L,"안근수"),new Member(3L,"박수빈"));
        //  when
        memberRepository.saveAll(members);
        //  then
        assertThat(memberRepository.findAll().size()).isEqualTo(2);
    }

    /*
        삭제 관련
        예를 들어 id = 2 인 멤버를 삭제할 때 SQL
        DELETE FROM member WHERE id = 2;

        JPA 에서는 deleteById() 를 통해 레코드를 삭제 가능
        아까 전에 추가와는 달리 @Sql 사용 예정
     */

    @Sql("/insert-members.sql")
    @Test
    void deleteMemberById(){
        //  when
        memberRepository.deleteById(2L);        // 이미 삭제가 끝남
        //  then           -> 2L 에 해당하는 객체를 찾았을 때 empty 인지 확인 할 예정
        assertThat(memberRepository.findById(2L).isEmpty()).isTrue();
    }
    /*
        수정 메서드
        ex) id 가 2인 멤버의 이름을 "BC" 로 바꾼다고 가정했을 때,

        수정 관련 SQL 문
        UPDATE member
        SET name = 'BC'
        WHERE id = 2;

        JPA 에서 데이터를 수정하는 방식은 추후에 SQL 에서 배우게 될 트랜잭션 내에서 데이터를 수행해야 함.
            -> 메서드만 사용하는 것이 아니라 @Transactional 애너테이션을 메서드에 추가해야함.

            Member.java
     */

    @Sql("/insert-members.sql")
    @Test
    void update(){
        //given     -> id 가 2인 객체를 가지고 와서 member 라는 객체명에 저장 / 현재 name = "B";
        Member member = memberRepository.findById(2L).get();

        //when
        member.changeName("BC");

        //then
        assertThat(memberRepository.findById(2L).get().getName()).isEqualTo("BC");
    }
    /*
        이상의 메서드에는 @Transactional 애터네이션이 존재하지 않음.
            -> @DataJpaTest 때문 (@Transactional 애너테이션이 이미 @DataJpaTest 에 상속받았기 때문)
     */
}