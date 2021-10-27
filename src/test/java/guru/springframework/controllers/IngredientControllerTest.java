package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngredientControllerTest {
    @Mock
    private IngredientService ingredientService;



    private IngrediantController ingrediantController;
    private  MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ingrediantController = new IngrediantController(ingredientService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingrediantController).build();
    }

    @Test
    void listIngredientTest() throws Exception{
        when(ingredientService.getRecipeCommand(anyLong())).thenReturn(any());
        mockMvc.perform(get("/recipe/1/ingredient"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredients/list"));
        verify(ingredientService,times(1)).getRecipeCommand(any());
    }

    @Test
    void showIngredientTest() throws Exception{
        when(ingredientService.getIngredient(anyLong(),anyLong())).thenReturn(new IngredientCommand());
        mockMvc.perform(get("/recipe/1/ingredient/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/ingredients/show"));
        verify(ingredientService,times(1)).getIngredient(anyLong(),anyLong());
    }
}
