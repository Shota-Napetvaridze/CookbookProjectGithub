package controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import main.MyListener;
import model.Recipe;

import java.net.URL;
import java.util.ResourceBundle;


public class RecipeController implements Initializable {

    @FXML
    private Label nameItem;

    @FXML
    private ImageView image;

    @FXML
    private ImageView heartImage;

    @FXML
    private Button heartButton;



    @FXML
    void favoritesClick(MouseEvent event) {myListener.favClickListener(recipe);}

    @FXML
    private void click(MouseEvent mouseEvent){
        myListener.onClickListener(recipe);
    }

    private Recipe recipe;
    private MyListener myListener;





    public void setData(Recipe recipe, MyListener myListener){
        this.recipe = recipe;
        this.myListener = myListener;
        nameItem.setText(recipe.getName());
        Image img = new Image(getClass().getResourceAsStream(recipe.getImgSrc()));
        image.setImage(img);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        heartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Image image = new Image("/main/filledWithLove.png");

                heartImage.setImage(image);
            }
        });
    }
}
