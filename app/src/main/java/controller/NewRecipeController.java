package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import main.DBUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class NewRecipeController implements Initializable {

    @FXML
    private Button add_image;

    @FXML
    private Button add_recipe;

    @FXML
    private ComboBox<?> comboBox2;

    @FXML
    private Button favorites2;

    @FXML
    private Button home2;

    @FXML
    private ImageView lbl_image;

    @FXML
    private Button logout2;

    @FXML
    private Button msg2;

    @FXML
    private Label msgCountLbl2;

    @FXML
    private Button plan2;

    @FXML
    private TextField txt_filename;

    @FXML
    private TextField txt_recipe_name;

    @FXML
    void select(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        home2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/main/fxmlFiles/home.fxml", null, null);
            }
        });
        logout2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "/main/fxmlFiles/login.fxml", null, null);
            }
        });
    }

}
