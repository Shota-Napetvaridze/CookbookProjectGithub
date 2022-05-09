package main;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import model.Ingredient;
import model.Recipe;
import model.Tag;

public interface MyListener {
    public void onClickListener(Recipe recipe);
    public void favClickListener(Recipe recipe, ImageView imageView);
    public void ingredientClickListener(Ingredient ingredient, Button ingredientButton);
    public void tagClickListener(Tag tag, Button tagButton);
    public void removeClickListener(Recipe recipe);
}
