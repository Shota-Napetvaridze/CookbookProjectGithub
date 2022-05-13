package services;

import java.util.List;

import models.entities.Ingredient;

public interface IngredientService {
    public List<Ingredient> getAllIngredients();
    public Ingredient getIngredientByName(String name);
}
