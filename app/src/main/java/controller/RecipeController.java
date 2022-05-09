package controller;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.MyListener;
import model.Recipe;
import javafx.scene.control.Button;
import main.DBUtils;

import java.net.URL;
import java.util.ResourceBundle;


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
    void click(MouseEvent mouseEvent){
        myListener.onClickListener(recipe);
    }


    @FXML
    void removeRecipe(MouseEvent event) {
        myListener.removeClickListener(recipe);

    }


    public void setData(Recipe recipe, MyListener myListener){
        this.recipe = recipe;
        this.myListener = myListener;
        recipeName.setText(recipe.getName());
       if (recipe.getImgSrc() != null) {           // <-------- sets data for sample recipes
           Image img = new Image(getClass().getResourceAsStream(recipe.getImgSrc()));
           image.setImage(img);
       } else {                                    // <-------- sets data for DB recipes
           Image img = recipe.getRecipeImage();
           image.setImage(img);
       }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        remove_recipe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/fxmlFiles/home.fxml",  null, null);
            }
        });
    }

}
