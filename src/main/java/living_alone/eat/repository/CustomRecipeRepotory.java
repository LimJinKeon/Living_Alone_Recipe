package living_alone.eat.repository;

import living_alone.eat.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomRecipeRepotory {

    Page<List<Recipe>> searchMemberRecipes(String keyword, Pageable pageable, String loginId);
}
