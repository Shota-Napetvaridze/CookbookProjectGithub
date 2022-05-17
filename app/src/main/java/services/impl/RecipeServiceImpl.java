package services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import models.entities.Ingredient;
import models.entities.Recipe;
import models.entities.Tag;
import services.RecipeService;
import util.common.DbContext;
import util.common.Validator;
import util.constants.SuccessMessages;
import util.constants.Variables;
import util.exceptions.common.InvalidCountException;
import util.exceptions.common.InvalidLengthException;
import util.exceptions.recipe.InvalidRecipeDescriptionLengthException;
import util.exceptions.recipe.InvalidRecipeIngredientsCountException;
import util.exceptions.recipe.InvalidRecipeInstructionsLengthException;
import util.exceptions.recipe.InvalidRecipeNameLengthException;
import util.exceptions.recipe.InvalidRecipeServingSizeException;
import util.exceptions.recipe.InvalidRecipeTagsCountException;
import javafx.scene.image.Image;

public class RecipeServiceImpl implements RecipeService {

    private DbContext dbContext;

    public RecipeServiceImpl() {
        super();
        dbContext = new DbContext(Variables.DATABASE_PORT, Variables.DATABASE_USER, Variables.DATABASE_PASS);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return dbContext.getAllRecipes();
    }

    @Override
    public Recipe getRecipeById(UUID id) {
        return dbContext.getRecipeById(id);
    }

    @Override
    public List<Recipe> getRecipesFiltered(Set<String> filters) {
        List<Recipe> allRecipes = dbContext.getAllRecipes();
        List<Recipe> filteredRecipes = new ArrayList<>();

        for (Recipe recipe : allRecipes) {
            boolean isValid = true;
            for (String filter : filters) {
                if (!recipe.toString().contains(filter)) {
                    isValid = false;
                    break;
                }
            }
            if (isValid) {
                filteredRecipes.add(recipe);
            }
        }

        return filteredRecipes;
    }

    @Override
    public String addRecipe(UUID recipeId, String name, String picturePath, String description,
            String instructions, UUID authorId,
            Map<Ingredient, Integer> ingredients, byte servingSize) throws InvalidRecipeNameLengthException, InvalidRecipeDescriptionLengthException, InvalidRecipeInstructionsLengthException, InvalidRecipeServingSizeException, InvalidRecipeTagsCountException, InvalidRecipeIngredientsCountException {
        validateName(name);
        validateDescription(description);
        validateInstructions(instructions);
        // validateTags(tags);
        validateIngredients(ingredients);
        validateServingSize(servingSize);
        dbContext.addRecipe(recipeId, name, picturePath, description, instructions, authorId);
        // for (Tag tag : tags) {
        //     if (dbContext.getTagById(tag.getId()) == null) {
        //         dbContext.addTag(tag.getId(), tag.getName());
        //     }
        //     dbContext.addRecipeTag(recipeId, tag.getId());
        // }
        Set<Ingredient> keys = ingredients.keySet();
        for (Ingredient ingredient : keys) {
            dbContext.addRecipeIngredient(recipeId, ingredient.getId(), ingredients.get(ingredient));
        }


        return String.format(SuccessMessages.RECIPE_ADDED);
    }

    @Override
    public String editRecipeName(UUID userId, String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String removeRecipe(UUID recipeId) {
        return dbContext.removeRecipeById(recipeId);
    }

    @Override
    public List<Recipe> getRecipesByNameLike(String name) {
        return dbContext.getRecipesByNameLike(name);
    }

    @Override
    public String editRecipeDescription(UUID recipeId, String description) throws InvalidRecipeDescriptionLengthException {
        validateDescription(description);
        return null;
    }

    @Override
    public String editRecipeInstructions(UUID recipeId, String instructions) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String editRecipeServingSize(UUID recipeId, byte servingSize) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void validateName(String name) throws InvalidRecipeNameLengthException {
        try {
            Validator.validateStringLength(name,
                    Variables.MIN_TAG_NAME_LENGTH, Variables.MAX_TAG_NAME_LENGTH);
        } catch (InvalidLengthException e) {
            throw new InvalidRecipeNameLengthException();
        }
    }

    @Override
    public void validateDescription(String description) throws InvalidRecipeDescriptionLengthException {
        try {
            Validator.validateStringLength(description, Variables.MIN_RECIPE_DESC_LENGTH,
                    Variables.MAX_RECIPE_DESC_LENGTH);
        } catch (InvalidLengthException e) {
            throw new InvalidRecipeDescriptionLengthException();
        }
    }

    @Override
    public void validateInstructions(String instructions) throws InvalidRecipeInstructionsLengthException {
        try {
            Validator.validateStringLength(instructions, Variables.MIN_RECIPE_INSTRUCTIONS_LENGTH,
                    Variables.MAX_RECIPE_INSTRUCTIONS_LENGTH);
        } catch (InvalidLengthException e) {
            throw new InvalidRecipeInstructionsLengthException();
        }
    }

    @Override
    public void validateServingSize(byte servingSize) throws InvalidRecipeServingSizeException {
        try {
            Validator.validateCount(servingSize, Variables.MIN_RECIPE_SERVING_SIZE, Variables.MAX_RECIPE_SERVING_SIZE);
        } catch (InvalidCountException e) {
            throw new InvalidRecipeServingSizeException();
        }
    }

    // @Override
    // public void validateTags(List<Tag> tags) throws InvalidRecipeTagsCountException {
    //     try {
    //         Validator.validateCount(tags.size(),
    //                 Variables.MIN_RECIPE_TAGS, Variables.MAX_RECIPE_TAGS);
    //     } catch (InvalidCountException e) {
    //         throw new InvalidRecipeTagsCountException();
    //     }
    // }

    @Override
    public void validateIngredients(Map<Ingredient, Integer> ingredients) throws InvalidRecipeIngredientsCountException {
        try {
            Validator.validateCount(ingredients.size(),
                        Variables.MIN_RECIPE_INGREDIENTS, Variables.MAX_RECIPE_INGREDIENTS);
        } catch (InvalidCountException e) {
            throw new InvalidRecipeIngredientsCountException();
        }        
    }

}
