package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToCommandRecipe;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Service
public class RecipeServiceImpl implements RecipeService{
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe getRecipeById(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if(! recipeOptional.isPresent()){
            throw  new RuntimeException("recipe is not found!!!!");
        }
        return recipeOptional.get();
    }

    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        RecipeCommandToRecipe converter = new RecipeCommandToRecipe();
        RecipeToCommandRecipe converter2 = new RecipeToCommandRecipe();
        Recipe recipe = converter.convert(recipeCommand);
        assert recipe != null;
        recipe = this.recipeRepository.save(recipe);
        return converter2.convert(recipe);

    }

    @Override
    public RecipeCommand findCommandById(Long id) {
        Optional<Recipe>  recipe = recipeRepository.findById(id);
        RecipeToCommandRecipe converter = new RecipeToCommandRecipe();
        return converter.convert(recipe.get());
    }
}
