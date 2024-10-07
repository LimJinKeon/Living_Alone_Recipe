package living_alone.eat.controller;

import living_alone.eat.SessionConst;
import living_alone.eat.domain.Member;
import living_alone.eat.domain.Recipe;
import living_alone.eat.service.MemberService;
import living_alone.eat.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final RecipeService recipeService;
    private final MemberService memberService;

    @GetMapping("/")
    public String mainPage() {
        return "welcome";
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<Recipe> recipes = recipeService.findAll();
        model.addAttribute("recipes", recipes);

        return "home";
    }
}
