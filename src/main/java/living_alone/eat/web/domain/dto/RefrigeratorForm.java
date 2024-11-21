package living_alone.eat.web.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RefrigeratorForm {

    private Long id;
    private String name;
    private int quantity;

    @Builder
    public RefrigeratorForm(Long id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }
}
