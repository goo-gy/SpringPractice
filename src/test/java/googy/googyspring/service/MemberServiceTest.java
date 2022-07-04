package googy.googyspring.service;

import googy.googyspring.domain.Member;
import googy.googyspring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    MemberService memberService = new MemberService(memberRepository);

        
    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void join() {
        // given
        Member member = new Member();
        member.setName("googy");

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
//        try{
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e) {
//            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//            System.out.println("join_duplicate success");
//        }

        // then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}