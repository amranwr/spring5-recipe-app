package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RecipeToCommandRecipeTest {
    private static final Long ID = 1L;
    private static final Difficulty DIFFICULTY = Difficulty.Easy;
    private static final String DESCRIPTION = "amr anwr";
    private RecipeToCommandRecipe recipeToCommandRecipe;
    @BeforeEach
    void setUp() {
        recipeToCommandRecipe = new RecipeToCommandRecipe();
    }

    @Test
    void convert() {
        Recipe recipe = new Recipe();
        recipe.setId(ID);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(DIFFICULTY);
        RecipeCommand recipeCommand = recipeToCommandRecipe.convert(recipe);
        assertNotNull(recipeCommand);
        assertEquals(recipe.getId(),recipeCommand.getId());
    }
}