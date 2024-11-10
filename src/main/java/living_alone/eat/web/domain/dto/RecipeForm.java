package living_alone.eat.web.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import living_alone.eat.web.annotation.ValidFile;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RecipeForm {

    @NotEmpty(message = "{required.recipeTitle}")
    private String recipeTitle;

    @NotEmpty(message = "{required.recipeContent}")
    private String recipeContent;

    @ValidFile
    private MultipartFile recipeImage;

    public RecipeForm(String recipeTitle, String recipeContent) {
        this.recipeTitle = recipeTitle;
        this.recipeContent = recipeContent;
    }
}
