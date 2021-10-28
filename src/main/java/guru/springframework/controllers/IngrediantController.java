package guru.springframework.controllers;

import guru.springframework.services.IngredientService;
import guru.springframework.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IngrediantController {
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngrediantController(IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping(path = "/recipe/{recipeId}/ingredient")
    public String listIngredients(@PathVariable String recipeId , Model model){
        model.addAttribute("recipe",ingredientService.getRecipeCommand(Long.valueOf(recipeId)));
        return "/recipe/ingredients/list";
    }

    @GetMapping
    @RequestMapping(path = "recipe/{recipeId}/ingredient/{ingredientId}")
    public String showIngredient(@PathVariable String recipeId,@PathVariable String ingredientId,Model model){
        model.addAttribute("ingredient",ingredientService.getIngredient(Long.valueOf(recipeId),Long.valueOf(ingredientId)));
        return "/recipe/ingredients/show";
    }

    @GetMapping
    @RequestMapping(path = "recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model){
        model.addAttribute("uomList",this.unitOfMeasureService.getUomList());
        model.addAttribute("ingredient",ingredientService.getIngredient(Long.valueOf(recipeId), Long.valueOf(ingredientId)));

        return "/recipe/ingredients/form";
    }
}
