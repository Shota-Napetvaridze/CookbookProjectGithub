package util.common;

import javafx.scene.control.TextArea;
import models.entities.Ingredient;
import models.entities.Message;
import models.entities.Recipe;
import models.entities.Tag;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;


public interface MyListener {
    public void onClickListener(Recipe recipe);
    public void favClickListener(Recipe recipe, ImageView imageView);
    public void ingredientClickListener(Ingredient ingredient, ImageView ingredientButton);
    public void tagClickListener(Tag tag, ImageView tagButton);

    public void recipeEntered(Recipe recipe, TextArea textArea);
    public void recipeExited(Recipe recipe, TextArea textArea);

    public void replyMsgListener(Message message, TextArea textArea);
}
