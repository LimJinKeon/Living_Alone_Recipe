package living_alone.eat.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IngredientImageStore {

    @Value("${ingredient.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename + ".png";
    }
}
