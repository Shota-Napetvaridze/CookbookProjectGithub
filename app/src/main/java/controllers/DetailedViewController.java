package controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import util.common.SceneContext;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class DetailedViewController implements Initializable {

    @FXML
    private Button close;

    @FXML
    private GridPane commentsGrid;

    @FXML
    private ScrollPane commentsScroll;

    @FXML
    private AnchorPane detailedAnchor;

    @FXML
    private Button editButton;

    @FXML
    private GridPane ingredientsGrid;

    @FXML
    private ScrollPane ingredietsScroll;

    @FXML
    private TextArea recipe_Description;

    @FXML
    private ImageView recipe_Img;

    @FXML
    private TextArea recipe_Instruction;

    @FXML
    private Label recipe_Name;

    @FXML
    private Button shareTheRecipe;

    @FXML
    private TextArea tagstextArea;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        shareTheRecipe.setOnAction(new EventHandler<ActionEvent>() {
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
