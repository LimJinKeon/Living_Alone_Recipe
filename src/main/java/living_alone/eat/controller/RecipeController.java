package living_alone.eat.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import living_alone.eat.web.session.SessionConst;
import living_alone.eat.domain.Member;
import living_alone.eat.domain.Recipe;
import living_alone.eat.file.FileStore;
import living_alone.eat.service.RecipeService;
import living_alone.eat.web.domain.dto.RecipeForm;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

@Controller
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final FileStore fileStore;

    @GetMapping
    public String myRecipe() {
        return "recipeForm";
    }

    @GetMapping("/new")
    public String newRecipe(@ModelAttribute("recipeForm") RecipeForm form) {
        return "addRecipeForm";
    }

    @PostMapping("/new")
    public String saveRecipe(@ModelAttribute RecipeForm form, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession(false);
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        recipeService.save(form, member);

        return "redirect:/myRecipeForm";
    }

    // 특정 레시피 가져오기
    @GetMapping("/{id}")
    public String items(@PathVariable Long id, Model model) {
        Optional<Recipe> recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe.orElse(null));
        return "recipeForm";
    }

    // 홈 화면 레시피 사진 가져오기
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }
}
