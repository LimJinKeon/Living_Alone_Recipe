package living_alone.eat.controller;

import living_alone.eat.SessionConst;
import living_alone.eat.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {

    @GetMapping("/")
    public String mainPage() {
        return "welcome";
    }

    @GetMapping("/home")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember) {
        //세션이 없으면 로그인으로
        if (loginMember == null) {
            return "members/loginForm";
        }
        //세션이 있으면 홈으로
        return "home";
    }
}
