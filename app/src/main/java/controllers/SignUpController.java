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
import services.UserService;
import services.impl.UserServiceImpl;
import util.common.SceneContext;

public class SignUpController implements Initializable {

    @FXML
    private Button signupNew;


    @FXML
    private Button button_login;


    @FXML
    private TextField usernameNew;


    @FXML
    private TextField email;

    @FXML
    private TextField passwordNew;

    private User user;
    
    private UserServiceImpl userService = new UserServiceImpl();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        signupNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!usernameNew.getText().trim().isEmpty() && !passwordNew.getText().trim().isEmpty()){
                    userService.addUser(usernameNew.getText(), email.getText(), passwordNew.getText());
                    user = userService.getUserByNickname(usernameNew.getText());
                    SceneContext.changeScene(event, "/fxmlFiles/home.fxml", user);

                    // DBUtils. (event, usernameNew.getText(), email.getText(), passwordNew.getText());
                }else {
                    System.out.println("Please fill in all information!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information to sign up!");
                    alert.show();
                }
            }
        });
        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneContext.changeScene(event, "/main/fxmlFiles/login.fxml", null);
            }
        });

    }
}