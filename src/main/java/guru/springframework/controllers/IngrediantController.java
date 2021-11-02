package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
        log.debug("getting ingredients  for that recipe id : " + recipeId);
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(recipeId)));
        return "/recipe/ingredients/list";
    }

    @GetMapping
    @RequestMapping(path = "recipe/{recipeId}/ingredient/{ingredientId}")
    public String showIngredient(@PathVariable String recipeId,@PathVariable String ingredientId,Model model){
        model.addAttribute("ingredient",ingredientService.getIngredientByRecipeId(Long.valueOf(recipeId),Long.valueOf(ingredientId)));
        return "/recipe/ingredients/show";
    }

    @GetMapping
    @RequestMapping(path = "recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model){
        model.addAttribute("uomList",this.unitOfMeasureService.getUomList());
        model.addAttribute("ingredient",ingredientService.getIngredientByRecipeId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        return "/recipe/ingredients/form";
    }

    @GetMapping
    @RequestMapping(path = "recipe/{recipeId}/ingredient/new")
    public String addNewIngredient(@PathVariable String recipeId,Model model){
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeCommand.getId());
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasure());
        model.addAttribute("ingredient",ingredientCommand);
        model.addAttribute("uomList",unitOfMeasureService.getUomList());
        return "recipe/ingredients/form";
    }

    @GetMapping
    @RequestMapping(path = "recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId,
                                   @PathVariable String ingredientId){

        ingredientService.deleteIngredient(Long.valueOf(recipeId), Long.valueOf(ingredientId));
        return "redirect:/recipe/"+recipeId+"/ingredient";
    }

    @PostMapping
    @RequestMapping(value = "recipe/{recipeId}/ingredient/updated")
    public String updateingredientList(@ModelAttribute IngredientCommand ingredientCommand){
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
        return "redirect:/recipe/"+savedIngredientCommand.getRecipeId()+"/ingredient";
    }
}
