package living_alone.eat.service;

import living_alone.eat.domain.Member;
import living_alone.eat.domain.Recipe;
import living_alone.eat.domain.UploadFile;
import living_alone.eat.exception.RecipeNotFoundException;
import living_alone.eat.file.FileStore;
import living_alone.eat.repository.MemberRepository;
import living_alone.eat.repository.RecipeRepository;
import living_alone.eat.web.domain.dto.RecipeForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;
    private final FileStore fileStore;

    // 레시피 저장
    @Transactional
    public Recipe save(RecipeForm form, String loginId) throws IOException {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + loginId));

        // 레시피 이미지 저장
        UploadFile uploadFile = fileStore.storeFile(form.getRecipeImage());

        // 레시피 생성
        Recipe recipe = Recipe.builder()
                            .member(member)
                            .recipeTitle(form.getRecipeTitle())
                            .recipeContent(form.getRecipeContent())
                            .uploadFileName(uploadFile.getUploadFileName())
                            .storeFileName(uploadFile.getStoreFileName())
                            .build();

        return recipeRepository.save(recipe);
    }

    // 레시피 수정
    @Transactional
    public Recipe update(Long id, RecipeForm form) throws IOException {
        Recipe recipe = findById(id).orElseThrow(RecipeNotFoundException::new);

        // 기존 레시피 삭제 후 저장
        fileStore.deleteFile(recipe.getStoreFileName());
        UploadFile uploadFile = fileStore.storeFile(form.getRecipeImage());

        recipe.update(form.getRecipeTitle(), form.getRecipeContent(),
                uploadFile.getUploadFileName(), uploadFile.getStoreFileName());

        return recipe;
    }

    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    public List<Recipe> findAllByMemberId(String loginId) {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow();

        Optional<List<Recipe>> recipes = recipeRepository.findAllByMemberId(member.getId());
        if (recipes.isPresent()) {
            return recipes.get();
        }
        return null;
    }
}
