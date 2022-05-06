package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import main.MyListener;
import model.Ingredient;
import model.Tag;

import java.net.URL;
import java.util.ResourceBundle;


public class IngredientController implements Initializable {

    @FXML
    private Label ingredientLbl;

    @FXML
    private Button ingredientButton;



    @FXML
    void ingredientClick(MouseEvent event) {
        myListener.ingredientClickListener(ingredient);
    }

    private Ingredient ingredient;
    private MyListener myListener;

    public void setData(Ingredient ingredient, MyListener myListener){
        this.ingredient = ingredient;
        this.myListener = myListener;
        ingredientLbl.setText(ingredient.getIngredientName());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ingredientButton.setShape(new Circle(1.5));
        ingredientButton.setMinWidth(20);
        ingredientButton.setMaxWidth(20);
        ingredientButton.setMinHeight(20);
        ingredientButton.setMaxHeight(20);
    }
}
