package living_alone.eat.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import living_alone.eat.domain.Member;
import living_alone.eat.exception.MyDuplicateIdException;
import living_alone.eat.service.MemberService;
import living_alone.eat.web.domain.dto.AddMemberForm;
import living_alone.eat.web.domain.dto.LoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/add")
    public String addMemberForm(@ModelAttribute("addMemberForm") AddMemberForm form) {
        return "members/addMemberForm";
    }

    // 회원 가입
    @PostMapping("/members/add")
    public String addMember(@Valid @ModelAttribute AddMemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/addMemberForm";
        }

        try {
            // 아이디 저장
            memberService.save(form);
        } catch (MyDuplicateIdException e) {
            // 아이디 중복
            result.rejectValue("loginId", "duplicate");
            return "members/addMemberForm";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "members/loginForm";
    }

    //로그아웃
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
