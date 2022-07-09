package googy.googyspring.service;

import googy.googyspring.domain.Member;
import googy.googyspring.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Commit
    void join() {
        // given
        Member member = new Member();
        member.setName("abcdefasd");

        // when
        Long saveId = memberService.join(member);

        // then
        Member result = memberService.findOne(saveId).get();
        Assertions.assertThat(result.getName()).isEqualTo(member.getName());
        System.out.println("join success");
    }

    @Test
    public void join_duplicate() {
        // given
        Member member1 = new Member();
        member1.setName("googy");

        Member member2 = new Member();
        member2.setName("googy");

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        System.out.println("join_duplicate success");
    }
}