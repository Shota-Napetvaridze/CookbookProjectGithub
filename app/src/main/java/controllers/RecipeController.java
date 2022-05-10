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

public class RecipeController implements Initializable {
    private Recipe recipe;
    private MyListener myListener;

    @FXML
    private Label recipeName;

    @FXML
    private ImageView image;

    @FXML
    private ImageView heartImage;

    @FXML
    private Button remove_recipe;

    @FXML
    void favoritesClick(MouseEvent event) {
        myListener.favClickListener(recipe, heartImage);
    }

    @FXML
    void click(MouseEvent mouseEvent) {
        myListener.onClickListener(recipe);
    }

    @FXML
    void removeRecipe(MouseEvent event) {
        myListener.removeClickListener(recipe);

    }

    public void setData(Recipe recipe, MyListener myListener) {
        this.recipe = recipe;
        this.myListener = myListener;
        recipeName.setText(recipe.getName());
        Image img = recipe.getPicture();
        image.setImage(img);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        remove_recipe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneContext.changeScene(event, "/fxmlFiles/home.fxml", null);
            }
        });
    }

}
