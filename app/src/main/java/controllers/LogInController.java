package controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    private UserServiceImpl userService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                User user = userService.loginUser(username.getText(), password.getText());
                if (user != null) {
                    SceneContext.changeScene(event, "home2.fxml", user);
                }
            }
        });

        signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneContext.changeScene(event, "/main/fxmlFiles/signUp.fxml",  null);
            }
        });

    }
}