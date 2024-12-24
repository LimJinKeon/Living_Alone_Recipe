package living_alone.eat.controller;

import living_alone.eat.domain.Refrigerator;
import living_alone.eat.exception.ImageNotFoundException;
import living_alone.eat.file.IngredientImageStore;
import living_alone.eat.service.RefrigeratorService;
import living_alone.eat.web.domain.dto.RefrigeratorForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;

import static living_alone.eat.config.UserSessionUtil.getCurrentLoginId;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/refrigerator/ingredients")
public class RefrigeratorController {

    private final RefrigeratorService refrigeratorService;
    private final IngredientImageStore ingredientImageStore;

    // 내 냉장고 폼
    @GetMapping("/add")
    public String refrigeratorForm(Model model) {
        // 내 냉장고의 모든 식재료 가져오기
        List<RefrigeratorForm> forms = refrigeratorService.findAllByMemberId(getCurrentLoginId());
        model.addAttribute("ingredients", forms);

        return "menu/refrigerator/addIngredientForm";
    }

    // 냉장고 재료 추가
    @PostMapping("/add")
    public String addIngredient(RefrigeratorForm form) {
        refrigeratorService.save(form);

        return "redirect:/refrigerator/ingredients/add";
    }

    // 냉장고 재료 수정 폼
    @GetMapping("/edit")
    public String editIngredient(Model model) {
        // 내 냉장고의 모든 식재료 가져오기
        List<RefrigeratorForm> forms = refrigeratorService.findAllByMemberId(getCurrentLoginId());
        model.addAttribute("ingredients", forms);

        return "menu/refrigerator/editIngredientForm";
    }

    // 재료 삭제
    @PostMapping("/{id}/delete")
    public String deleteIngredient(@PathVariable("id") Long id) {
        refrigeratorService.deleteIngredient(id);
        return "redirect:/refrigerator/ingredients/edit";
    }

    // 재료 수량 증가
    @PostMapping("/{id}/increment")
    public String incrementQuantity(@PathVariable("id") Long id) {
        refrigeratorService.incrementQuantity(id);
        return "redirect:/refrigerator/ingredients/edit";
    }

    // 재료 수량 감소
    @PostMapping("/{id}/decrement")
    public String decrementQuantity(@PathVariable("id") Long id) {
        refrigeratorService.decrementQuantity(id);
        return "redirect:/refrigerator/ingredients/edit";
    }

    // 재료 일러스트 가져오기
    @ResponseBody
    @GetMapping("/images/{ingredientName}")
    public Resource downloadImage(@PathVariable String ingredientName) {
        try {
            return new UrlResource("file:" + ingredientImageStore.getFullPath(ingredientName));
        } catch (MalformedURLException e) {
            log.info("식재료 이미지가 없습니다.");
            throw new ImageNotFoundException(e);
        }
    }
}
