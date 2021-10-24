package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import org.springframework.core.convert.converter.Converter;

public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if(ingredient == null){
            return null;
        }
        final IngredientCommand ingredientCommand= new IngredientCommand();
        ingredientCommand.setId(ingredient.getId());
        ingredientCommand.setAmount(ingredient.getAmout());
        ingredientCommand.setDescription(ingredient.getDescription());
        return ingredientCommand;
    }
}
