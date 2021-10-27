package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Ingredient;

import java.util.Set;

public interface IngredientService {
    RecipeCommand getRecipeCommand(Long recipeId);
}
