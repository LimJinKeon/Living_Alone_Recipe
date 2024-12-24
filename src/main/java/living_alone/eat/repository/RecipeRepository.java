package living_alone.eat.repository;

import living_alone.eat.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, CustomRecipeRepotory {

    Page<Recipe> findAllByMemberId(Pageable pageable, Long id);
    Page<Recipe> findAll(Pageable pageable);
    Page<Recipe> findByRecipeTitleContaining(String keyword, Pageable pageable);
}
