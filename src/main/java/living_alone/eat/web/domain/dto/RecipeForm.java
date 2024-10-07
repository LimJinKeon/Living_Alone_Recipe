package living_alone.eat.web.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RecipeForm {

    @NotEmpty(message = "요리명을 적어주세요")
    private String cookName;

    @NotEmpty(message = "코멘트를 남겨주세요")
    private String description;

    @NotEmpty(message = "사진을 넣어주세요")
    private MultipartFile imageFile;
}
