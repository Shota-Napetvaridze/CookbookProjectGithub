package util.common;

import models.entities.Ingredient;
import models.entities.Recipe;
import models.entities.Tag;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;


public interface MyListener {
    public void onClickListener(Recipe recipe);
    public void favClickListener(Recipe recipe, ImageView imageView);
    public void ingredientClickListener(Ingredient ingredient, Button ingredientButton);
    public void tagClickListener(Tag tag, Button tagButton);
}
