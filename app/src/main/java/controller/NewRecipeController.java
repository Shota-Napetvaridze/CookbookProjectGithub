package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import main.DBUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class NewRecipeController implements Initializable {

    @FXML
    private Button logout2;

    @FXML
    private Button newRecipe2home;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        newRecipe2home.setOnAction(new EventHandler<ActionEvent>() {
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
