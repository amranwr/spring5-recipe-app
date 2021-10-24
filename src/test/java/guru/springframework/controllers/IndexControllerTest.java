package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

class IndexControllerTest {
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private Model model;
    private IndexController indexController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeRepository);
    }


    @Test
    void testMockMVC()throws Exception{
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
    }

    @Test
    void getIndexPage() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        Recipe recipe1 = new Recipe();
        recipe1.setId(1l);
        Recipe recipe2 =  new Recipe();

        recipes.add(recipe1);
        recipes.add(recipe2);
        when(recipeRepository.findAll()).thenReturn(recipes);
        ArgumentCaptor<ArrayList<Recipe>> argumentCaptor = ArgumentCaptor.forClass(ArrayList.class);

        assertEquals("index",indexController.getIndexPage(model));
        verify(recipeRepository,times(1)).findAll();
        verify(model,times(1)).addAttribute(eq("recipes"),argumentCaptor.capture());
        assertEquals(2,argumentCaptor.getValue().size());
    }
}