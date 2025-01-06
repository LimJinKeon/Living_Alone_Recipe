package living_alone.eat.repository;

import living_alone.eat.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomRecipeRepotory {

    Page<List<Recipe>> findAllOrderByCreate(Pageable pageable);
    Page<List<Recipe>> searchRecipesLike(String keyword, Pageable pageable);
    Page<List<Recipe>> findAllByMemberId(Pageable pageable, String loginId);
    Page<List<Recipe>> searchMemberRecipesLike(String keyword, Pageable pageable, String loginId);
}
