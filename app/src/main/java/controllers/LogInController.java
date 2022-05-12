package controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

import models.entities.User;
import services.impl.UserServiceImpl;
import util.common.SceneContext;

public class LogInController implements Initializable {
    @FXML
    private Button login;

    @FXML
    private Button signup;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    private UserServiceImpl userService = new UserServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                User user = userService.loginUser(username.getText(), password.getText());
                if (user != null) {
                    SceneContext.user = user;
                    SceneContext.changeScene(event, "/fxmlFiles/home.fxml");
                }
                else {
                    System.out.println("User not found!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Provided Credentials are incorrect");
                    alert.show();
                }
            }
        });

        signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneContext.changeScene(event, "/fxmlFiles/signUp.fxml");
            }
        });

    }
}