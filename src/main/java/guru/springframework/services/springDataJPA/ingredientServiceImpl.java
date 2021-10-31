package guru.springframework.services.springDataJPA;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.services.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ingredientServiceImpl implements IngredientService {
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientRepository ingredientRepository;
    private  final RecipeRepository recipeRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private  final UnitOfMeasureRepository unitOfMeasureRepository;

    public ingredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientRepository ingredientRepository, RecipeRepository recipeRepository, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand getIngredientByRecipeId(Long recipeId, Long ingredientId) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
        if(!optionalRecipe.isPresent()){
            //todo impl error handling
            log.error("there is no recipe with that id"+recipeId);
        }
        Recipe recipe = optionalRecipe.get();

        Optional<IngredientCommand> optionalIngredientCommand = recipe.getIngrediants().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert).findFirst();

        if(!optionalIngredientCommand.isPresent()){
            //todo error handling
            log.error("there is no ingredient with that id :"+ingredientId);
        }
        return optionalIngredientCommand.get();
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
        if(! recipeOptional.isPresent()){
            //todo error handling
            log.error("no recipe with that id : "+ingredientCommand.getRecipe().getId());
            return new IngredientCommand();
        }else{
            Optional<Ingredient> ingredient = recipeOptional.get().getIngrediants().stream()
                    .filter(ingredient1 -> ingredient1.getId().equals(ingredientCommand.getId()))
                    .findFirst();
            if(ingredient.isPresent()){
                Ingredient realIngredient = ingredient.get();
                realIngredient.setDescription(ingredientCommand.getDescription());
                realIngredient.setAmout(ingredientCommand.getAmout());
                Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId());
                if(unitOfMeasureOptional.isPresent()){
                    realIngredient.setUnitOfMeasure(unitOfMeasureOptional.get());
                }else{
                    throw new RuntimeException("uom is not found");
                }

            }else{
                recipeOptional.get().addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
            }
            recipeRepository.save(recipeOptional.get());
            return ingredientToIngredientCommand.convert(recipeOptional.get().getIngrediants()
                    .stream().filter(ingredient1 -> ingredient1.getId().equals(ingredientCommand.getId()))
                    .findFirst().get());
        }
    }
}
