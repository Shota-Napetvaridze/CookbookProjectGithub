package controllers;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

import models.entities.Recipe;
import util.common.MyListener;
import util.common.SceneContext;

public class RecipeController  {
    private Recipe recipe;
    private MyListener myListener;

    @FXML
    private Label recipeName;

    @FXML
    private ImageView image;

    @FXML
    private ImageView heartImage;


    @FXML
    void favoritesClick(MouseEvent event) {
        myListener.favClickListener(recipe, heartImage);
    }

    @FXML
    void click(MouseEvent mouseEvent) {
        myListener.onClickListener(recipe);
    }



    public void setData(Recipe recipe, MyListener myListener) {
        this.recipe = recipe;
        this.myListener = myListener;
        recipeName.setText(recipe.getName());
        image.setImage(recipe.getPicture());
    }

}
