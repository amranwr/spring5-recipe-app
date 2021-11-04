package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;


import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void uploadImage() throws Exception{
        MockMultipartFile multiPartFile = new MockMultipartFile("imagefile",
                "testing.txt", MediaType.TEXT_PLAIN_VALUE,"amr".getBytes());
        mockMvc.perform(multipart("/recipe/1/image").file(multiPartFile))
                .andExpect(status().is3xxRedirection());
        verify(imageService,times(1)).saveImageFile(anyLong(),any());
    }
 
    @Test
    void testingRenderImage()throws Exception{
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        String s = "amr";
        Byte[] image = new Byte[s.getBytes().length];
        int  i = 0;
        for(Byte item : s.getBytes()){
            image[i++]= item;
        }
        recipeCommand.setImage(image);
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/renderImage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        byte[] actualResponse  = response.getContentAsByteArray();
        assertEquals(actualResponse.length,image.length);
    }
}