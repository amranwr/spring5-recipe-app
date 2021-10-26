package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import org.springframework.core.convert.converter.Converter;

public class RecipeToCommandRecipe implements Converter<Recipe, RecipeCommand> {
    @Override
    public RecipeCommand convert(Recipe recipe) {
        if(recipe == null){
            return  null;
        }
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipe.getId());
        recipeCommand.setCategories(recipe.getCategories());
        recipeCommand.setCookTime(recipe.getCookTime());
        recipeCommand.setDescription(recipe.getDescription());
        recipeCommand.setDifficulty(recipe.getDifficulty());
        recipeCommand.setDirections((recipe.getDirections()));
        recipeCommand.setImage(recipe.getImage());
        recipeCommand.setIngredients((recipe.getIngrediants()));
        recipeCommand.setNote(recipe.getNote());
        recipeCommand.setPrepTime(recipe.getPrepTime());
        recipeCommand.setServings(recipe.getServings());
        recipeCommand.setSource(recipe.getSource());
        recipeCommand.setUrl(recipe.getUrl());
        return recipeCommand;
    }
}
