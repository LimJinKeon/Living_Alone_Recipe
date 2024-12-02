package living_alone.eat.controller;

import living_alone.eat.domain.Recipe;
import living_alone.eat.service.KamisService;
import living_alone.eat.service.RecipeService;
import living_alone.eat.web.domain.dto.kamis.DailyPriceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final RecipeService recipeService;
    private final KamisService kamisService;

    // welcome 화면
    @GetMapping("/")
    public String mainPage() {
        return "welcome";
    }

    // 홈 화면
    @GetMapping("/home")
    public String home(Model model) throws Exception {
        List<Recipe> recipes = recipeService.findAll();
        List<DailyPriceDto> recentlyPriceTrend = kamisService.getRecentlyPriceTrend();
        model.addAttribute("recipes", recipes);
        model.addAttribute("recentlyPriceTrend", recentlyPriceTrend);

        return "home";
    }
}
