package living_alone.eat.controller;

import living_alone.eat.domain.Recipe;
import living_alone.eat.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final RecipeService recipeService;

    // welcome 화면
    @GetMapping("/")
    public String mainPage() {
        return "welcome";
    }

    // 홈 화면
    @GetMapping("/home")
    public String home(Model model) {
        List<Recipe> recipes = recipeService.findAll();
        model.addAttribute("recipes", recipes);

        return "home";
    }
}
