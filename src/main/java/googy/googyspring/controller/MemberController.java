package googy.googyspring.controller;

import googy.googyspring.domain.Member;
import googy.googyspring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class MemberController {
    private MemberService memberService;
    static Long id = 0L;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("member/register")
    public String createForm() {
        return "member/registerForm";
    }

    @PostMapping("member/register")
    @ResponseBody
    public RedirectView memberRegister(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/");
        return redirectView;
    }

    @GetMapping("members")
    @ResponseBody
    public List<Member> members() {
        List<Member> memberList =  memberService.findMembers();
        return memberList;
    }
}
