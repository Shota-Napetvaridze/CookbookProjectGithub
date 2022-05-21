package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.entities.Ingredient;
import util.common.NewRecipeListener;
import util.common.UserListener;

public class IngredientItemController {
    private Ingredient ingredient;
    private UserListener userListener;
    private NewRecipeListener newRecipeListener;

    @FXML
    private TextField amountField;

    @FXML
    private ImageView ingredientButton;

    @FXML
    private Label ingredientLbl;

    @FXML
    private ImageView cart;

    @FXML
    private Button button;

    @FXML
    private Label ingredientUnit;

    @FXML
    void buttonAction(MouseEvent event) {
        if (caller.equals("DetailedViewController")) {
            userListener.addIngredientToCart(ingredient, quantity);
        } else if (caller.equals("NewRecipeController")) {
            newRecipeListener.addIngredientToRecipe(ingredient, Integer.parseInt(amountField.getText()));
        } else if (caller.equals("SelectedNewRecipeController")) {
            newRecipeListener.removeIngredientFromRecipe(ingredient);
        } else if (caller.equals("CartController")) {
            userListener.removeIngredientFromCart(ingredient);
        }
    }

    private Integer quantity;
    private String caller;

    public void setData(NewRecipeListener newRecipeListener) {
        this.newRecipeListener = newRecipeListener;
    }

    public void setData(Ingredient ingredient, Integer quantity, String caller, UserListener userListener){
        this.caller = caller;
        this.ingredient = ingredient;
        this.userListener = userListener;
        this.quantity = quantity;
        ingredientLbl.setText(ingredient.getName());
        ingredientUnit.setText(ingredient.getUnit());
        if (caller.equals("DetailedViewController")) {
            amountField.setEditable(false);
            amountField.setText(quantity.toString());
            button.setText("+");
        } else if (caller.equals("NewRecipeController")) {
            amountField.setPromptText(quantity.toString());
            cart.setVisible(false);
            button.setText("+");
        } else if (caller.equals("SelectedNewRecipeController")) {
            amountField.setText(quantity.toString());
            cart.setVisible(false);
            button.setText("-");
        } else if (caller.equals("CartController")) {
            amountField.setText(quantity.toString());
            cart.setVisible(false);
            button.setText("-");
        }
    }

}
