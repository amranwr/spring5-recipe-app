package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class RecipeControllerTest {
    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    private RecipeController recipeController;
    private MockMvc  mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    void getRecipe()throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        when(recipeService.getRecipeById(anyLong())).thenReturn(recipe);
        mockMvc.perform(get("/show/recipe/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/show"));
    }

    @Test
    void getRecipeNotFound() throws Exception{
        when(recipeService.getRecipeById(1L)).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/show/recipe/1"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404Error"));
    }

    @Test
    void numberFormateException() throws Exception{
        mockMvc.perform(get("/show/recipe/amr"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400Error"));
    }
/*
    @Test
    void addRecipeTest()throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);
        mockMvc.perform(post("/recipe")
                .param("id","1")
                .param("description","some string")
                                .param("directions","some direction")
                                .param("cookTime","0")
                                .param("prepTime","4")
                                .param("servings","4")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/show/recipe/1"));
    }
*/
    @Test
    void updateRecipe() throws Exception{
        when(model.addAttribute(any())).thenReturn(any());
        mockMvc.perform(get("/recipe/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"));

    }

    @Test
    void deleteRecipe() throws Exception{
        mockMvc.perform(get("/deleteRecipe/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
        verify(recipeService,times(1)).deleteRecipeCommand(any());
    }
}