package living_alone.eat.service;

import living_alone.eat.domain.Member;
import living_alone.eat.domain.Recipe;
import living_alone.eat.domain.UploadFile;
import living_alone.eat.exception.RecipeNotFoundException;
import living_alone.eat.file.RecipeImageStore;
import living_alone.eat.repository.MemberRepository;
import living_alone.eat.repository.RecipeRepository;
import living_alone.eat.web.domain.dto.RecipeForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;
    private final RecipeImageStore recipeImageStore;

    // 레시피 저장
    @Transactional
    public Recipe save(RecipeForm form, String loginId) throws IOException {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + loginId));

        // 레시피 이미지 저장
        UploadFile uploadFile = recipeImageStore.storeFile(form.getRecipeImage());

        // 레시피 생성
        Recipe recipe = Recipe.builder()
                            .member(member)
                            .recipeTitle(form.getRecipeTitle())
                            .recipeContent(form.getRecipeContent())
                            .uploadFileName(uploadFile.getUploadFileName())
                            .storeFileName(uploadFile.getStoreFileName())
                            .build();
        log.info(recipe.toString());

        return recipeRepository.save(recipe);
    }

    // 레시피 수정
    @Transactional
    public Recipe update(Long id, RecipeForm form) throws IOException {
        Recipe recipe = findById(id).orElseThrow(RecipeNotFoundException::new);

        // 기존 레시피 삭제 후 저장
        recipeImageStore.deleteFile(recipe.getStoreFileName());
        UploadFile uploadFile = recipeImageStore.storeFile(form.getRecipeImage());

        recipe.update(form.getRecipeTitle(), form.getRecipeContent(),
                uploadFile.getUploadFileName(), uploadFile.getStoreFileName());
        log.info(recipe.toString());

        return recipe;
    }

    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    // 모든 레시피를 가져오는 페이징 코드
    public Page<List<Recipe>> findAllOrderByCreate(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return recipeRepository.findAllOrderByCreate(pageable);
    }

    // 해당 검색어가 들어간 모든 레시피를 가져오는 페이징 코드
    public Page<List<Recipe>> searchRecipesLike(String keyword, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return recipeRepository.searchRecipesLike(keyword, pageable);
    }

    // 해당 사용자의 모든 레시피를 가져오는 페이징 코드
    public Page<List<Recipe>> findAllByMemberId(int page, int limit, String loginId) {
        Pageable pageable = PageRequest.of(page, limit);
        return recipeRepository.findAllByMemberId(pageable, loginId);
    }

    // 해당 검색어가 들어간 특정 사용자의 모든 레시피를 가져오는 페이징 코드
    public Page<List<Recipe>> searchMemberRecipesLike(String keyword, int page, int limit, String loginId) {
        Pageable pageable = PageRequest.of(page, limit);
        return recipeRepository.searchMemberRecipesLike(keyword, pageable, loginId);
    }

    // 특정 레시피 삭제
    @Transactional
    public void deleteById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        // 레시피 사진 삭제
        if (recipe.isPresent()) {
            recipeImageStore.deleteFile(recipe.get().getStoreFileName());
        }
        recipeRepository.deleteById(id);
    }
}
