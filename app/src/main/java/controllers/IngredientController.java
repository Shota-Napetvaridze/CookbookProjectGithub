package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import java.net.URL;
import java.util.ResourceBundle;

import models.entities.Ingredient;
import util.common.MyListener;


public class IngredientController implements Initializable{
    private Ingredient ingredient;
    private MyListener myListener;

    @FXML
    private Label ingredientLbl;

    @FXML
    private ImageView ingredientButton;

    @FXML
    private void ingredientClick(MouseEvent event) {
        myListener.ingredientClickListener(ingredient, ingredientButton);
    }

    public void setData(Ingredient ingredient, MyListener myListener){
        this.ingredient = ingredient;
        this.myListener = myListener;
        ingredientLbl.setText(ingredient.getName());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
