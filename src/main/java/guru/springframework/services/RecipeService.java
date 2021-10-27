package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;

import java.util.ArrayList;
import java.util.Set;


public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe getRecipeById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand findCommandById(Long id);

    void deleteRecipeCommand(Long id);
}
