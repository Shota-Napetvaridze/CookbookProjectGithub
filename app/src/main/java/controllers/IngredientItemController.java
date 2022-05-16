package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.entities.Ingredient;
import util.common.MyListener;

public class IngredientItemController {
    private Ingredient ingredient;
    private MyListener myListener;

    @FXML
    private Label amount;

    @FXML
    private ImageView ingredientButton;

    @FXML
    private Label ingredientLbl;

    @FXML
    void addToCartClick(MouseEvent event) {
        myListener.ingredientClickListener(ingredient, ingredientButton);
    }

    @FXML
    private void ingredientClick(MouseEvent event) {
        myListener.ingredientClickListener(ingredient, ingredientButton);
    }

    public void setData(Ingredient ingredient, MyListener myListener){
        this.ingredient = ingredient;
        this.myListener = myListener;
        ingredientLbl.setText(ingredient.getName());
    }

}
