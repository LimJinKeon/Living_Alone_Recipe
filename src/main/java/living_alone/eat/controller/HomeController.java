package living_alone.eat.controller;

import living_alone.eat.domain.Recipe;
import living_alone.eat.service.KamisService;
import living_alone.eat.service.RecipeService;
import living_alone.eat.web.domain.dto.kamis.DailyPriceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final RecipeService recipeService;
    private final KamisService kamisService;

    // welcome 페이지
    @GetMapping("/")
    public String mainPage() {
        return "welcome";
    }

    // 홈 페이지
    @GetMapping("/home")
    public String home(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "9") int size,
                       Model model) throws Exception {
        Page<List<Recipe>> recipePage = recipeService.findAllOrderByCreate(page, size);
        List<DailyPriceDto> recentlyPriceTrend = kamisService.getRecentlyPriceTrend();

        model.addAttribute("recipes", recipePage.getContent().getFirst());  // 현재 페이지 데이터
        model.addAttribute("currentPage", recipePage.getNumber());          // 현재 페이지 번호
        model.addAttribute("totalPages", recipePage.getTotalPages());       // 전체 페이지 수
        model.addAttribute("recentlyPriceTrend", recentlyPriceTrend);       // 농산물 당일 가격 데이터

        return "home";
    }

    // 레시피 검색 결과
    @GetMapping("/home/search")
    public String searchRecipes(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            Model model) {

        Page<List<Recipe>> recipePage = recipeService.searchRecipesLike(keyword, page, size);

        model.addAttribute("recipes", recipePage.getContent().getFirst());   // 현재 페이지 데이터
        model.addAttribute("currentPage", recipePage.getNumber());           // 현재 페이지 번호
        model.addAttribute("totalPages", recipePage.getTotalPages());        // 전체 페이지 수
        model.addAttribute("keyword", keyword);                              // 농산물 당일 가격 데이터

        return "homeSearchRecipe";
    }
}
