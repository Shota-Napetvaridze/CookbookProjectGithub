package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.entities.Ingredient;
import util.common.UserListener;

public class IngredientItemController {
    private Ingredient ingredient;
    private UserListener userListener;

    @FXML
    private Label amount;

    @FXML
    private ImageView ingredientButton;

    @FXML
    private Label ingredientLbl;

    @FXML
    void addToCartClick(MouseEvent event) {
        userListener.ingredientClickListener(ingredient, ingredientButton);
    }

    @FXML
    private void ingredientClick(MouseEvent event) {
        userListener.ingredientClickListener(ingredient, ingredientButton);
    }

    public void setData(Ingredient ingredient, UserListener userListener){
        this.ingredient = ingredient;
        this.userListener = userListener;
        ingredientLbl.setText(ingredient.getName());
    }

}
