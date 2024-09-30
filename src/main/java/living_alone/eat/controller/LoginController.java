package living_alone.eat.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import living_alone.eat.SessionConst;
import living_alone.eat.domain.Member;
import living_alone.eat.exception.MyDuplicateId;
import living_alone.eat.service.LoginService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final LoginService loginService;

    @GetMapping("/members/add")
    public String addMemberForm(@ModelAttribute("addMemberForm") AddMemberForm form) {
        return "members/addMemberForm";
    }

    @PostMapping("/members/add")
    public String addMember(@Valid @ModelAttribute AddMemberForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "members/addMemberForm";
        }

        try {
            memberService.save(form);
        } catch (MyDuplicateId e) {
            result.rejectValue("loginId", "duplicate");
            return "members/addMemberForm";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "members/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
                        HttpServletRequest request, @RequestParam(defaultValue = "/home") String redirectURL) {
        if (bindingResult.hasErrors()) {
            return "members/loginForm";
        }
        Member loginMember = loginService.login(form);

        //로그인 실패
        if (loginMember == null) {
            bindingResult.rejectValue("password", "invalid");
            return "members/loginForm";
        }

        //로그인 성공
        log.info("login {}", loginMember.getLoginId());
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:" + redirectURL;
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
