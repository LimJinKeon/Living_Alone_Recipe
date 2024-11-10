package living_alone.eat.repository;

import living_alone.eat.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    public Optional<List<Recipe>> findAllByMemberId(Long id);
}
