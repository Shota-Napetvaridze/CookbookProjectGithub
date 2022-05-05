package main;

import javafx.scene.image.ImageView;
import model.Recipe;

public interface MyListener {
    public void onClickListener(Recipe recipe);
    public void favClickListener(Recipe recipe, ImageView imageView);
}
