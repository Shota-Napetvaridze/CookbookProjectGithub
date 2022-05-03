package cookbook.services;

import java.util.Set;

import cookbook.models.entities.Ingredient;
import cookbook.models.entities.Recipe;
import cookbook.models.entities.Tag;
import cookbook.models.entities.User;

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
