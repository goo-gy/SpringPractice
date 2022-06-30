package googy.googyspring.repository;

import googy.googyspring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("googy");

        repository.save(member);
        Member result = repository.findById(member.getId()).get();
        Assertions.assertThat(member).isEqualTo(result);
        System.out.println("save success");
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("member1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("member2");
        repository.save(member2);

        Member result = repository.findByName("member1").get();
        Assertions.assertThat(member1).isEqualTo(result);

        Member result2 = repository.findByName("member2").get();
        Assertions.assertThat(member2).isEqualTo(result2);

        System.out.println("findByName success");
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("member1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("member1");
        repository.save(member2);

        List<Member> result = repository.findAll();
        Assertions.assertThat(result.size()).isEqualTo(2);
        System.out.println("findAll success");
    }

}
