package living_alone.eat.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Ingredient {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private LocalDate Expiration;

    @OneToMany(mappedBy = "ingredient")
    private List<RefrigeratorIngredient> ingredients;
}
