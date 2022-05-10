package services;

import models.entities.Ingredient;

public interface IngredientService {
    public Ingredient getIngredientByName(String name);
}
