package guru.springframework.services.springDataJPA;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
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

    @Override
    public IngredientCommand getIngredient(Long recipeId, Long ingredientId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if(!optionalRecipe.isPresent()){
            log.error("there is no recipe with that id"+recipeId);
        }

        Set<Ingredient> ingredients = optionalRecipe.get().getIngrediants();
        Ingredient temp = null;
        for(Ingredient ingredient:ingredients){
            if(ingredient.getId() == ingredientId){
                temp = ingredient;
                break;
            }
        }
        IngredientToIngredientCommand ingredientToIngredientCommand = new IngredientToIngredientCommand();
        if(temp == null){
            log.error("there is no ingredient with that id :"+ingredientId);
        }
        return ingredientToIngredientCommand.convert(temp);
    }
}
