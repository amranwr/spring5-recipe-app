package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {
    @Mock
    private IngredientService ingredientService;
    @Mock
    private UnitOfMeasureService unitOfMeasureService;
    @Mock
    private RecipeService recipeService;

    private IngrediantController ingrediantController;
    private  MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ingrediantController = new IngrediantController(ingredientService, unitOfMeasureService, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingrediantController).build();
    }

    @Test
    void listIngredientTest() throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        mockMvc.perform(get("/recipe/1/ingredient"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredients/list"))
                .andExpect(model().attributeExists("recipe"));
        verify(recipeService,times(1)).findCommandById(any());
    }

    @Test
    void showIngredientTest() throws Exception{
        when(ingredientService.getIngredientByRecipeId(anyLong(),anyLong())).thenReturn(new IngredientCommand());
        mockMvc.perform(get("/recipe/1/ingredient/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredients/show"))
                .andExpect(model().attributeExists("ingredient"));
        verify(ingredientService,times(1)).getIngredientByRecipeId(anyLong(),anyLong());
    }

    @Test
    void updateIngredient()throws Exception{
        when(ingredientService.getIngredientByRecipeId(anyLong(),anyLong())).thenReturn(new IngredientCommand());
        when(unitOfMeasureService.getUomList()).thenReturn(new HashSet<>());
        mockMvc.perform(get("/recipe/1/ingredient/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredients/form"))
                .andExpect(model().attributeExists("uomList"))
                .andExpect(model().attributeExists("ingredient"));
        verify(ingredientService,times(1)).getIngredientByRecipeId(anyLong(),anyLong());
        verify(unitOfMeasureService,times(1)).getUomList();
    }

    @Test
    public void updateingredientList()throws Exception{
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1L);
        //test new ingredient
        //ingredientCommand.setId(1L);
        ingredientCommand.setRecipeId(1L);
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        ingredientCommand.setRecipe(recipe);
        when(ingredientService.saveIngredientCommand(any())).thenReturn(ingredientCommand);
        mockMvc.perform(post("/recipe/1/ingredient/updated")
                .param("ingredientCommand",""))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredient"));
        verify(ingredientService,times(1)).saveIngredientCommand(any());

    }


}
