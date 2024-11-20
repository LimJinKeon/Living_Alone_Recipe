package living_alone.eat.repository;

import living_alone.eat.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    public Optional<Ingredient> findByName(String name);
}
