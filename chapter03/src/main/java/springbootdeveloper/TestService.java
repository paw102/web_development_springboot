package springbootdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {
    @Autowired
    MemberRepository memberRepository;  // Bean 주입

    public List<Member> getAllMembers(){
        return memberRepository.findAll();  // 멤버 목록 얻기
    }
}

/*
    1. MamberRepository 라는 빈을 주입 후
    2. findAll() 메서드를 호출하여 멤버 테이블에 저장된 멤버 목록을 가져옴.
 */
