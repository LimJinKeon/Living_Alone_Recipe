package living_alone.eat.controller;

import living_alone.eat.domain.Refrigerator;
import living_alone.eat.file.IngredientImageStore;
import living_alone.eat.repository.RefrigeratorRepository;
import living_alone.eat.service.RefrigeratorService;
import living_alone.eat.web.domain.dto.RefrigeratorForm;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/refrigerator/ingredients")
public class RefrigeratorController {

    private final RefrigeratorRepository refrigeratorRepository;
    private final RefrigeratorService refrigeratorService;
    private final IngredientImageStore ingredientImageStore;

    // 내 냉장고 폼
    @GetMapping
    public String refrigeratorForm(Model model) {
        List<Refrigerator> ingredients = refrigeratorRepository.findAll();
        List<RefrigeratorForm> forms = new ArrayList<>();

        for (Refrigerator ingredient : ingredients) {
            forms.add(RefrigeratorForm.builder()
                        .name(ingredient.getName())
                        .quantity(ingredient.getQuantity())
                        .build());

        }
        model.addAttribute("ingrediens", forms);
        return "menu/refrigerator";
    }

    // 냉장고 재료 추가
    @PostMapping
    public String addIngredient(RefrigeratorForm form) {
        refrigeratorService.save(form);

        return "redirect:/refrigerator/ingredients";
    }

    // 재료 일러스트 가져오기
    @ResponseBody
    @GetMapping("/images/{ingredientName}")
    public Resource downloadImage(@PathVariable String ingredientName) {
        try {
            return new UrlResource("file:" + ingredientImageStore.getFullPath(ingredientName));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
