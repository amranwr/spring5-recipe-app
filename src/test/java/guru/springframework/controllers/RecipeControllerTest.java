package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

class RecipeControllerTest {
    @Mock
    private RecipeService recipeRepository;

    @Mock
    private Model model;

    private RecipeController recipeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeRepository);
    }

    @Test
    void getRecipe()throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        when(recipeRepository.getRecipeById(anyLong())).thenReturn(recipe);
        mockMvc.perform(get("/show/recipe/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recipe/show"));
    }

    @Test
    void addRecipeTest()throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        when(recipeRepository.saveRecipeCommand(any())).thenReturn(recipeCommand);
        mockMvc.perform(post("/recipe")
                .param("id","1")
                .param("description","some string"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/show/recipe/1"));
    }

    @Test
    void updateRecipe() throws Exception{
        when(model.addAttribute(any())).thenReturn(any());
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        mockMvc.perform(get("/recipe/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"));

    }

    @Test
    void deleteRecipe() throws Exception{
        MockMvc mockMvc  = MockMvcBuilders.standaloneSetup(recipeController).build();
        mockMvc.perform(get("/deleteRecipe/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
        verify(recipeRepository,times(1)).deleteRecipeCommand(any());
    }
}