package living_alone.eat.service;

import living_alone.eat.domain.Member;
import living_alone.eat.domain.Recipe;
import living_alone.eat.domain.UploadFile;
import living_alone.eat.file.FileStore;
import living_alone.eat.repository.RecipeRepository;
import living_alone.eat.web.domain.dto.RecipeForm;
import lombok.RequiredArgsConstructor;
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
    private final FileStore fileStore;

    public Recipe save(RecipeForm form, Member member) throws IOException {
        UploadFile uploadFile = fileStore.storeFile(form.getImageFile());

        Recipe recipe = new Recipe(uploadFile.getUploadFileName(), uploadFile.getStoreFileName(),
                member, form.getCookName(), form.getDescription());

        return recipeRepository.save(recipe);
    }

    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }
}
