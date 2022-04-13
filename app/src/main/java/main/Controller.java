package main;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button login;

    @FXML
    private Button signup;

    @FXML
    private TextField username;


    @FXML
    private TextField password;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.logInUser(event, username.getText(), password.getText());
            }
        });

        signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "signUp.fxml",  null, null);
            }
        });

    }
}