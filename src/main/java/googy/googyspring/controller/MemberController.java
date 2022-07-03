package googy.googyspring.controller;

import googy.googyspring.domain.Member;
import googy.googyspring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;
    static Long id = 1L;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("member/register")
    @ResponseBody
    Member memberAPI(@RequestParam("name") String name) {
        Member member = new Member();
        member.setId(id);
        id++;
        member.setName(name);
        memberService.join(member);
        return member;
    }


    @GetMapping("members")
    @ResponseBody
    public List<Member> memberAPI() {
        List<Member> memberList =  memberService.findMembers();
        return memberList;
    }
}
