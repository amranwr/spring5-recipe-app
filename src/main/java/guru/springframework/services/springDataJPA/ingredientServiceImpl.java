package guru.springframework.services.springDataJPA;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeToCommandRecipe;
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
    private final IngredientRepository ingredientRepository;
    private  final RecipeRepository recipeRepository;

    public ingredientServiceImpl(IngredientRepository ingredientRepository, RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public RecipeCommand getRecipeCommand(Long recipeId) {
        RecipeToCommandRecipe recipeToCommandRecipe = new RecipeToCommandRecipe();
        return recipeToCommandRecipe.convert(recipeRepository.findById(recipeId).get());

    }
}
