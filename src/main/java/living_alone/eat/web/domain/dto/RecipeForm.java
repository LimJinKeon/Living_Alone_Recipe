package living_alone.eat.web.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RecipeForm {

    @NotEmpty(message = "{required.cookName}")
    private String cookName;

    @NotEmpty(message = "{required.description}")
    private String description;

    @NotEmpty(message = "{required.picture}")
    private MultipartFile imageFile;
}
