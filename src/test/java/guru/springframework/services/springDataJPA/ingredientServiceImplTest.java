package guru.springframework.services.springDataJPA;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.services.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ingredientServiceImplTest {
    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private RecipeRepository recipeRepository;
    private IngredientService ingredientService;
    private final IngredientToIngredientCommand ingredientToIngredientCommand = new IngredientToIngredientCommand();
    private final IngredientCommandToIngredient ingredientCommandToIngredient = new IngredientCommandToIngredient();
    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientService = new ingredientServiceImpl(ingredientToIngredientCommand,ingredientRepository,recipeRepository, ingredientCommandToIngredient, unitOfMeasureRepository);

    }

    @Test
    void saveIngredientCommand() {
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);
        Optional<UnitOfMeasure> unitOfMeasure= Optional.of(new UnitOfMeasure());
        unitOfMeasure.get().setId(1L);
        command.setUnitOfMeasure(unitOfMeasure.get());
        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredient(new Ingredient());
        savedRecipe.getIngrediants().iterator().next().setId(3L);
        when(unitOfMeasureRepository.findById(anyLong())).thenReturn(unitOfMeasure);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        //then
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void deleteIngredient(){
        Optional<Recipe> recipeOptional = Optional.of(new Recipe());
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        recipeOptional.get().addIngredient(ingredient);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        ingredientService.deleteIngredient(1L,1L);
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(ingredientRepository,times(1)).delete(any());
    }

}