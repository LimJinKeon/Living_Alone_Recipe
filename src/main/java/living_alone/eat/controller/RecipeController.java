package living_alone.eat.controller;

import jakarta.validation.Valid;
import living_alone.eat.domain.Member;
import living_alone.eat.domain.Recipe;
import living_alone.eat.exception.RecipeNotFoundException;
import living_alone.eat.file.RecipeImageStore;
import living_alone.eat.service.MemberService;
import living_alone.eat.service.RecipeService;
import living_alone.eat.web.domain.dto.RecipeForm;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static living_alone.eat.config.UserSessionUtil.getCurrentLoginId;

@Controller
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeImageStore recipeImageStore;
    private final MemberService memberService;

    // 내 레시피
    @GetMapping
    public String myRecipe(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "12") int size,
                           Model model) {
        Page<Recipe> recipePage = recipeService.findAllByMemberId(page, size, getCurrentLoginId());

        model.addAttribute("myRecipes", recipePage.getContent());       // 현재 페이지 데이터
        model.addAttribute("currentPage", recipePage.getNumber());      // 현재 페이지 번호
        model.addAttribute("totalPages", recipePage.getTotalPages());   // 전체 페이지 수

        return "menu/recipes/myRecipe";
    }

    // 내 레시피
    @GetMapping("/search")
    public String mySearchRecipe(
                @RequestParam(defaultValue = "") String keyword,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "12") int size,
                Model model) {
        Page<List<Recipe>> recipePage = recipeService.searchMemberRecipes(keyword, page, size, getCurrentLoginId());

        model.addAttribute("myRecipes", recipePage.getContent().getFirst());    // 현재 페이지 데이터
        model.addAttribute("currentPage", recipePage.getNumber());              // 현재 페이지 번호
        model.addAttribute("totalPages", recipePage.getTotalPages());           // 전체 페이지 수

        return "menu/recipes/mySearchRecipe";
    }

    // 신규 레시피 저장 폼
    @GetMapping("/new")
    public String newRecipe(@ModelAttribute("recipeForm") RecipeForm form) {
        return "menu/recipes/addRecipeForm";
    }

    // 신규 레시피 저장
    @PostMapping("/new")
    public String saveRecipe(@Valid @ModelAttribute RecipeForm form, BindingResult result, RedirectAttributes redirectAttributes) throws IOException {
        if (result.hasErrors()) {
            return "menu/recipes/addRecipeForm";
        }

        Recipe recipe = recipeService.save(form, getCurrentLoginId());
        redirectAttributes.addAttribute("recipeId", recipe.getId());

        return "redirect:/recipes/{recipeId}";
    }

    // 레시피 수정 폼
    @GetMapping("/edit/{id}")
    public String editRecipeForm(@PathVariable("id") Long id, Model model) {
        Recipe recipe = recipeService.findById(id).orElseThrow(RecipeNotFoundException::new);

        RecipeForm recipeForm = new RecipeForm(recipe.getRecipeTitle(), recipe.getRecipeContent());
        model.addAttribute("editRecipeForm", recipeForm);

        return "menu/recipes/editRecipeForm";
    }

    // 레시피 수정
    @PostMapping("/edit/{id}")
    public String editRecipe(@Valid @ModelAttribute("editRecipeForm") RecipeForm form, BindingResult result,
                             @PathVariable("id") Long id) throws IOException {
        if (result.hasErrors()) {
            return "menu/recipes/editRecipeForm";
        }
        recipeService.update(id, form);

        return "redirect:/recipes/{id}";
    }

    // 레시피 삭제
    @GetMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable("id") Long id) {
        Recipe recipe = recipeService.findById(id).orElseThrow(RecipeNotFoundException::new);
        recipeService.deleteById(id);

        return "redirect:/recipes";
    }

    // 특정 레시피 가져오기
    @GetMapping("/{id}")
    public String items(@PathVariable Long id, Model model) {
        Recipe recipe = recipeService.findById(id).orElse(null);
        Member member = memberService.findByLoginId(getCurrentLoginId()).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다"));
        boolean myRecipe = false;

        // 내 레시피인지 확인
        if (member.getId() == recipe.getMember().getId()) myRecipe = true;

        // LocalDateTime을 특정 포맷으로 변환 후 문자열을 반환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedCreatedDate = recipe.getCreatedDate().format(formatter);
        String formattedModifiedDate = recipe.getLastModifiedDate().format(formatter);

        model.addAttribute("recipe", recipe);
        model.addAttribute("myRecipe", myRecipe);
        model.addAttribute("createdDate", formattedCreatedDate);
        model.addAttribute("modifiedDate", formattedModifiedDate);

        return "menu/recipes/recipeForm";
    }

    // 레시피 사진 가져오기
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + recipeImageStore.getFullPath(filename));
    }
}
