package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.springDataJPA.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    private RecipeServiceImpl recipeServiceImpl;

    @Mock
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeServiceImpl = new RecipeServiceImpl(recipeRepository);

    }

    @Test
    void getRecipes() {

        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());

        when(recipeServiceImpl.getRecipes()).thenReturn(recipes);
        Set<Recipe> recipeSet = recipeServiceImpl.getRecipes();
        assertEquals(recipes.size(),recipeSet.size());
    }

    @Test
    void getRecipeById(){
        Long id = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(id);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(any())).thenReturn(recipeOptional);
        assertEquals(recipeServiceImpl.getRecipeById(id),recipeRepository.findById(any()).get());
        assertNotNull(recipeServiceImpl.getRecipeById(id));
        verify(recipeRepository,times(2)).findById(anyLong());
    }


}