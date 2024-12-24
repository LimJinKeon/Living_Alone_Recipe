package living_alone.eat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import living_alone.eat.domain.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static living_alone.eat.domain.QRecipe.recipe;

public class RecipeRepositoryImpl implements CustomRecipeRepotory {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public RecipeRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 특정 검색어가 포함된 내 레시피 가져오기
    @Override
    public Page<List<Recipe>> searchMemberRecipes(String keyword, Pageable pageable, String loginId) {
        // 전체 레시피 데이터를 페치
        List<Recipe> recipes = queryFactory
                .selectFrom(recipe)
                .where(recipe.member.loginId.eq(loginId)
                        .and(recipe.recipeTitle.like("%" + keyword + "%")))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(recipe.createdDate.desc())
                .fetch();

        // 조건에 맞는 전체 Recipe 개수를 카운트
        long total = queryFactory.select(recipe.count())
                .from(recipe)
                .where(recipe.member.loginId.eq(loginId)
                        .and(recipe.recipeTitle.like("%" + keyword + "%")))
                .fetchOne();

        // Page 객체로 변환하여 반환
        return new PageImpl<>(
                List.of(recipes),
                pageable,
                total);
    }
}
