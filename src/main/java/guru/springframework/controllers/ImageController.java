package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {
    private ImageService imageService;
    private RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{recipeId}/image")
    public String uploadImageForm(@PathVariable String recipeId,Model model){
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(recipeId)));
        return "recipe/imageForm";
    }
    @PostMapping("recipe/{recipeId}/image")
    public String uploadImage(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file){
        imageService.saveImageFile(Long.valueOf(recipeId),file);
        return "redirect:/show/recipe/"+recipeId;
    }

    @GetMapping("recipe/{recipeId}/renderImage")
    public void renderImage(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
        RecipeCommand recipeOptional = recipeService.findCommandById(Long.valueOf(recipeId));
        System.out.println("what the hell is going on !!!!!!!!!!!!");
        if(recipeOptional.getImage() != null) {
            System.out.println("hey there");
            byte[] myImage = new byte[recipeOptional.getImage().length];
            int i = 0;
            for (Byte item : recipeOptional.getImage()) {
                myImage[i++] = item; //auto unboxing
            }
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(myImage);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
