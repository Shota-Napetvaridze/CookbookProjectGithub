package services;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import models.entities.Recipe;
import javafx.scene.image.Image;

public interface RecipeService {
    public List<Recipe> getAllRecipes();
    public Recipe getRecipeById(UUID id);
    public Recipe getRecipeByName(String name);
    public List<Recipe> getRecipesFiltered(Set<String> filters);
    public List<Recipe> getFavoriteRecipes(UUID userId);

    public String addRecipe(UUID id, String name, Image picture, String description,
    String instructions, UUID authorId);

    public String editRecipe(UUID userId, UUID oldRecipeId, UUID newRecipeId);
    public String removeRecipe(UUID recipeId);
    public List<Recipe> getDinnerList();
}
