package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {
   // @Autowired
    private IngredientCommandToIngredient ingredientCommandToIngredient;

    @BeforeEach
    void setUp() {
        ingredientCommandToIngredient = new IngredientCommandToIngredient();
    }

    @Test
    void convert() {
        IngredientCommand ingredient =  new IngredientCommand();
        ingredient.setId(1L);
        ingredient.setAmout(BigDecimal.valueOf(2));
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();

        unitOfMeasure.setId(1L);
        unitOfMeasure.setUom("amroo");

        ingredient.setUnitOfMeasure(unitOfMeasure);
        ingredient.setDescription("helllllloooo");

        Ingredient oriIngredient = ingredientCommandToIngredient.convert(ingredient);
        assertNotNull(oriIngredient);
        assertEquals(oriIngredient.getId(),ingredient.getId());
    }
}