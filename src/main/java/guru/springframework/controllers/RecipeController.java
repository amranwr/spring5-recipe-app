package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {
    private final RecipeService recipeRepository;

    public RecipeController(RecipeService recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping(path = "/show/recipe/{id}")
    public String getRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe",this.recipeRepository.getRecipeById(Long.parseLong(id)));
        return "/recipe/show";
    }

    @RequestMapping(path = "recipe/new")
    public String createNewRecipe(Model model){
        model.addAttribute("recipe" , new RecipeCommand());
        return "recipe/recipeform";
    }

    @RequestMapping(path = "/recipe/update/{id}")
    public String updateRecipe(@PathVariable Long id ,Model model){
        model.addAttribute("recipe",recipeRepository.findCommandById(id));
        return "recipe/recipeform";
    }

    @PostMapping
    @RequestMapping(value = "recipe")
    public String addRecipe(@ModelAttribute RecipeCommand command){
        RecipeCommand savedCommand = recipeRepository.saveRecipeCommand(command);
        return "redirect:/show/recipe/"+ savedCommand.getId();
    }


    @RequestMapping(value="/deleteRecipe/{id}")
    public String deleteRecipe(@PathVariable Long id){
        recipeRepository.deleteRecipeCommand(id);
        return "redirect:/index";
    }
}
