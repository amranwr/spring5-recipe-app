package guru.springframework.controllers;

import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IngrediantController {
    private final IngredientService ingredientService;

    public IngrediantController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @RequestMapping(path = "/recipe/{recipeId}/ingredient")
    public String listIngredients(@PathVariable String recipeId , Model model){
        model.addAttribute("recipe",ingredientService.getRecipeCommand(Long.valueOf(recipeId)));
        return "/recipe/ingredients/list";
    }
}
