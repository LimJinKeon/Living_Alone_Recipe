package living_alone.eat.web.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RefrigeratorForm {

    private String name;
    private int quantity;

    @Builder
    public RefrigeratorForm(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
