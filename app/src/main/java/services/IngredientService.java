package services;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import models.entities.Ingredient;

public interface IngredientService {
    public List<Ingredient> getAllIngredients();
    public Ingredient getIngredientById(UUID id);
    public List<Ingredient> getIngredientByNameLike(String name);
    public Map<Ingredient, Integer> getIngredientsByRecipeId(UUID recipeId);
}
