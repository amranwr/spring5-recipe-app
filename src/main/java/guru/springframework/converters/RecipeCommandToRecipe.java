package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    @Override
    public Recipe convert(RecipeCommand recipe) {
        if(recipe == null){
            return  null;
        }
        Recipe recipeCommand = new Recipe();
        recipeCommand.setId(recipe.getId());
        recipeCommand.setCategories(recipe.getCategories());
        recipeCommand.setCookTime(recipe.getCookTime());
        recipeCommand.setDescription(recipe.getDescription());
        recipeCommand.setDifficulty(recipe.getDifficulty());
        recipeCommand.setDirections((recipe.getDirections()));
        recipeCommand.setImage(recipe.getImage());
        recipeCommand.setIngrediants((recipe.getIngrediants()));
        recipeCommand.setNote(recipe.getNote());
        recipeCommand.setPrepTime(recipe.getPrepTime());
        recipeCommand.setServings(recipe.getServings());
        recipeCommand.setSource(recipe.getSource());
        recipeCommand.setUrl(recipe.getUrl());
        return recipeCommand;
    }
}
