package guru.springframework.services.springDataJPA;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile imageFile) {
        try {
            System.out.println("hello--------------");
            Optional<Recipe> recipe = recipeRepository.findById(recipeId);
            if(!recipe.isPresent()){
                throw new IOException("recipe not found with that id : "+ recipe.get().getId());
            }
            Recipe originalRecipe = recipe.get();
            Byte imageBytes[] = new Byte[imageFile.getBytes().length];
            int i = 0 ;
            for(byte item: imageFile.getBytes()){
                imageBytes[i++] = item;
            }
            originalRecipe.setImage(imageBytes);
            recipeRepository.save(originalRecipe);
        }catch (IOException e){
            log.error("IOException : "+ e);
            e.printStackTrace();
        }
        log.debug("saving image file");
    }
}
