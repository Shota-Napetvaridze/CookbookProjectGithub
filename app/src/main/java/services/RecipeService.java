package services;

import java.util.Set;

import models.entities.Ingredient;
import models.entities.Recipe;
import models.entities.Tag;
import models.entities.User;

public interface RecipeService {
    public Set<Recipe> getRecipes();
    public Recipe getRecipe(String name);
    public Set<Recipe> getRecipesFiltered(Set<Tag> tags, Set<Ingredient> ingredients);
    public Set<Recipe> getFavoriteRecipes();
    public String addRecipe(User user, Recipe recipe);
    public String editRecipe(User user, Recipe oldRecipe, Recipe newRecipe);
    public String removeRecipe(Recipe recipe);
    public Set<Recipe> getDinnerList();
}
