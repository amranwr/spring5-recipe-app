package guru.springframework.controllers;

import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class ImageControllerTest {
    @Mock
    private RecipeService recipeService;
    @Mock
    private ImageService imageService;
    @InjectMocks
    private ImageController imageController;

    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void uploadImageForm() throws Exception{
        when(recipeService.findCommandById(anyLong())).thenReturn(any());
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/imageForm"));
        verify(recipeService,times(1)).findCommandById(anyLong());
    }
/*
    @Test
    void uploadImage() throws Exception{
        System.out.println("hello");
        MockMultipartFile multiPartFile = new MockMultipartFile("filename",
                "testing.txt", MediaType.TEXT_PLAIN_VALUE,"amr".getBytes());
        System.out.println("there");
        mockMvc.perform(multipart("/recipe/1/image").file(multiPartFile))
                .andExpect(status().is3xxRedirection());
        verify(imageService,times(1)).saveImageFile(anyLong(),any());
    }

 */
}