package living_alone.eat.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import living_alone.eat.domain.Member;
import living_alone.eat.exception.MyDuplicateId;
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
public class LoginController {

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
        } catch (MyDuplicateId e) {
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

    /*// 로그인
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
                        HttpServletRequest request, @RequestParam(defaultValue = "/home") String redirectURL) {
        if (bindingResult.hasErrors()) {
            return "members/loginForm";
        }

        Boolean result = checkIdPassword(form, bindingResult);

        //로그인 실패
        if (!result) return "members/loginForm";

        //로그인 성공
        log.info("login {}", form.getLoginId());
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, form.getLoginId());

        return "redirect:" + redirectURL;
    }*/

    private Boolean checkIdPassword(LoginForm form, BindingResult bindingResult) {
        Optional<Member> member = memberService.findByLoginId(form.getLoginId());

        // 아이디 확인
        if (member.isPresent()) {
            if (!member.get().getPassword().equals(form.getPassword())) {
                bindingResult.rejectValue("password", "invalid");
                return false;
            }
        } else {
            bindingResult.rejectValue("loginId", "invalid");
            return false;
        }
        return true;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
