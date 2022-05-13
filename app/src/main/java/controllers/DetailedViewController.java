package controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import models.entities.Recipe;
import services.impl.IngredientServiceImpl;
import util.common.SceneContext;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class DetailedViewController implements Initializable {

    @FXML
    private Button close;


    @FXML
    private TextArea recipe_Description;

    @FXML
    private ImageView recipe_Img;

    @FXML
    private TextArea recipe_Instruction;

    @FXML
    private Label recipe_Name;

    @FXML
    private Button sendTheRecipe;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sendTheRecipe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();

            }
        });

        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneContext.changeScene(event, "/fxmlFiles/home.fxml");
            }

        });
    }


}
