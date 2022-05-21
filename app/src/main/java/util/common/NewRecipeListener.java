package util.common;

import models.entities.Ingredient;

public interface NewRecipeListener {
    public void addIngredientToRecipe(Ingredient ingredient, Integer quantity);

    public void removeIngredientFromRecipe(Ingredient ingredient);
}
