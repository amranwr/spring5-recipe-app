package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
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
    public String addRecipe(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().stream().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "recipe/recipeform";
        }
        RecipeCommand savedCommand = recipeRepository.saveRecipeCommand(command);
        return "redirect:/show/recipe/"+ savedCommand.getId();
    }


    @RequestMapping(value="/deleteRecipe/{id}")
    public String deleteRecipe(@PathVariable Long id){
        recipeRepository.deleteRecipeCommand(id);
        return "redirect:/index";
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView load404ErrorPage(Exception exception){
        log.error("catching not found exception");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404Error");
        modelAndView.addObject("exception",exception);
        return modelAndView;
    }


}
