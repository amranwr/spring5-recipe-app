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

import java.util.Optional;

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
            Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId());
            if(!unitOfMeasureOptional.isPresent()){
                throw new RuntimeException("uom is not found");
            }else{
                ingredientCommand.setUnitOfMeasure(unitOfMeasureOptional.get());
            }
            if(ingredient.isPresent()){
                Ingredient realIngredient = ingredient.get();
                realIngredient.setDescription(ingredientCommand.getDescription());
                realIngredient.setAmout(ingredientCommand.getAmout());
                realIngredient.setUnitOfMeasure(unitOfMeasureOptional.get());
            }else{
                Ingredient original = ingredientCommandToIngredient.convert(ingredientCommand);
                original.setUnitOfMeasure(unitOfMeasureOptional.get());
                original.setRecipe(recipeOptional.get());
                recipeOptional.get().addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
            }
            Recipe savedRecipe = recipeRepository.save(recipeOptional.get());
            Optional<Ingredient> optionalIngredient = savedRecipe.getIngrediants().stream()
                    .filter(ingredient1 -> ingredient1.getId().equals(ingredientCommand.getId()))
                    .findFirst();
            if(!optionalIngredient.isPresent()){
                optionalIngredient = savedRecipe.getIngrediants().stream()
                    .filter(ingredient1 -> ingredient1.getDescription().equals(ingredientCommand.getDescription()))
                    .filter(ingredient1 -> ingredient1.getAmout().equals(ingredientCommand.getAmout()))
                    .filter(ingredient1 -> ingredient1.getUnitOfMeasure().equals(ingredientCommand.getUnitOfMeasure()))
                    .findFirst();
            }
            return ingredientToIngredientCommand.convert(optionalIngredient.get());
        }
    }

    @Override
    public boolean deleteIngredient(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if(recipeOptional.isPresent()){
            Optional<Ingredient> ingredientOptional = recipeOptional.get().getIngrediants().stream()
                    .filter(ingredient1 -> ingredient1.getId().equals(ingredientId))
                    .findFirst();
            if(ingredientOptional.isPresent()){

                recipeOptional.get().getIngrediants().remove(ingredientOptional.get());
                ingredientRepository.delete(ingredientOptional.get());
                return true;
            }else{
                log.error("there is no ingredient with that id : "+ ingredientId);
                return false;
            }
        }else{
            log.error("there is no recipe with that id : "+ recipeId);
            return false;
        }
    }
}
