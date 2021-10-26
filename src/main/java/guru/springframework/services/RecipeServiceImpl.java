package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToCommandRecipe;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.OptionalInt;
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
    public Recipe saveRecipeCommand(RecipeCommand recipeCommand) {
        RecipeCommandToRecipe converter = new RecipeCommandToRecipe();
        Recipe recipe = converter.convert(recipeCommand);
        recipe = this.recipeRepository.save(recipe);
        return recipe;

    }
}
