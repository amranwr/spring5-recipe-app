package guru.springframework.services.springDataJPA;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ImageServiceImplTest {
    @Mock
    private RecipeRepository recipeRepository;
    private ImageService imageService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    void saveImageFile() throws IOException {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("amr","anwr", MediaType.TEXT_PLAIN_VALUE,"wala".getBytes(StandardCharsets.UTF_8));
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        imageService.saveImageFile(1L,mockMultipartFile);


        verify(recipeRepository,times(1)).save(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue().getImage().length,mockMultipartFile.getBytes().length);
    }
}