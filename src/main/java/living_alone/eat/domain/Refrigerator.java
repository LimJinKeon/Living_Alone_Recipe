package living_alone.eat.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
public class Refrigerator {

    @Id @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "refrigerator")
    private Member member;

    @OneToMany(mappedBy = "refrigerator", cascade = CascadeType.ALL)
    private List<RefrigeratorIngredient> ingredients;

    public void addIngredient(RefrigeratorIngredient ingredient) {
        ingredients.add(ingredient);
        ingredient.setRefrigerator(this);
    }

}
