package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IngrediantController {
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;
    private final RecipeService recipeService;

    public IngrediantController(IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService, RecipeService recipeService) {
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
        this.recipeService = recipeService;
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

    @GetMapping
    @RequestMapping(path = "recipe/{recipeId}/ingredient/new}")
    public String addNewIngredient(@PathVariable String recipeId,Model model){
        model.addAttribute("ingredient",recipeService.getRecipeById(Long.valueOf(recipeId)));
        model.addAttribute("uomList",unitOfMeasureService.getUomList());
        return "recipe/ingredients/new";
    }

    @PostMapping
    @RequestMapping(value = "ingredient")
    public String updateingredientList(@ModelAttribute IngredientCommand ingredientCommand){
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
        System.out.println("hello"+savedIngredientCommand.getRecipe().getId());
        return "redirect:/recipe/"+savedIngredientCommand.getRecipe().getId()+"/ingredient";
    }
}
