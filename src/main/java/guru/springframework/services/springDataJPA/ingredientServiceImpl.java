package guru.springframework.services.springDataJPA;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ingredientServiceImpl implements IngredientService {
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientRepository ingredientRepository;
    private  final RecipeRepository recipeRepository;

    public ingredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientRepository ingredientRepository, RecipeRepository recipeRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand getIngredientByRecipeId(Long recipeId, Long ingredientId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if(!optionalRecipe.isPresent()){
            //todo impl error handling
            log.error("there is no recipe with that id"+recipeId);
        }
        Recipe recipe = optionalRecipe.get();

        Optional<IngredientCommand> optionalIngredientCommand = recipe.getIngrediants().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if(!optionalIngredientCommand.isPresent()){
            //todo error handling
            log.error("there is no ingredient with that id :"+ingredientId);
        }
        return optionalIngredientCommand.get();
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        IngredientCommandToIngredient converter = new IngredientCommandToIngredient();
        Ingredient ingredient = ingredientRepository.save(converter.convert(ingredientCommand));
        assert ingredient!=null;
        IngredientToIngredientCommand converter2 = new IngredientToIngredientCommand();
        return converter2.convert(ingredient);
    }
}
