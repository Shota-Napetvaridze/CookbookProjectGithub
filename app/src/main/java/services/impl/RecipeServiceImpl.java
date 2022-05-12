package services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import models.entities.Recipe;
import services.RecipeService;
import util.common.DbContext;
import util.constants.Variables;
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
    public List<Recipe> getFavoriteRecipes(UUID userId) {
        return dbContext.getFavoriteRecipes(userId);
    }

    @Override
    public String addRecipe(UUID id, String name, Image picture, String description,
    String instructions, UUID authorId) {
        return dbContext.addRecipe(id, name, picture, description, instructions, authorId);
    }

    @Override
    public String editRecipe(UUID userId, UUID oldRecipeId, UUID newRecipeId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String removeRecipe(UUID recipeId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Recipe> getDinnerList() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Recipe getRecipeByName(String name) {
        return dbContext.getRecipeByName(name);
    }


    
}
