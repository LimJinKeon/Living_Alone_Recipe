package living_alone.eat.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Ingredient {

    @Id @GeneratedValue
    private long id;

    private String name;
    private LocalDate Expiration;

    @OneToMany(mappedBy = "ingredient")
    private List<RefrigeratorIngredient> refIngre = new ArrayList<>();
}
