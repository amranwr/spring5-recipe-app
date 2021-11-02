package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Ingredient;

import java.util.Set;

public interface IngredientService {
    IngredientCommand getIngredientByRecipeId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
    boolean deleteIngredient(Long recipeId, Long ingredientId);
}
