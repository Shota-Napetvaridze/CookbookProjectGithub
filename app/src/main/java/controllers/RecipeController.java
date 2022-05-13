package controllers;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import models.entities.Recipe;
import util.common.MyListener;
import util.common.SceneContext;

public class RecipeController implements Initializable{
    private Recipe recipe;
    private MyListener myListener;

    @FXML
    private Label recipeName;

    @FXML
    private ImageView image;

    @FXML
    private ImageView heartImage;

    @FXML
    private TextArea recipeDescriptionHover;


    @FXML
    void favoritesClick(MouseEvent event) {
        myListener.favClickListener(recipe, heartImage);
    }

    @FXML
    void click(MouseEvent mouseEvent) {
        myListener.onClickListener(recipe);
    }

    @FXML
    void descriptionEntered(MouseEvent event) {
        myListener.recipeEntered(recipe, recipeDescriptionHover);
    }
    @FXML
    void descriptionExited(MouseEvent event) {
        myListener.recipeExited(recipe, recipeDescriptionHover);
    }



    public void setData(Recipe recipe, MyListener myListener) {
        this.recipe = recipe;
        this.myListener = myListener;
        recipeName.setText(recipe.getName());
        image.setImage(recipe.getPicture());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        ScrollBar scrollBar = (ScrollBar)ta.lookup(".scroll-bar:vertical");
//        scrollBar.setDisable(true);
    }
}
