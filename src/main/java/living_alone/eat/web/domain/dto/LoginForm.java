package living_alone.eat.web.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginForm {

    @NotEmpty(message = "{required.id}")
    private String loginId;

    @NotEmpty(message = "{required.password}")
    private String password;
}
