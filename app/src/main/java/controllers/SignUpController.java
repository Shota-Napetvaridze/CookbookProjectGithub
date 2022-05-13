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
import util.exceptions.common.InvalidLengthException;
import util.exceptions.user.InvalidEmailException;
import util.exceptions.user.InvalidNicknameLengthException;
import util.exceptions.user.InvalidPasswordComplexityException;
import util.exceptions.user.InvalidPasswordLengthException;
import util.exceptions.user.InvalidUserNameLengthException;
import util.exceptions.user.TakenEmailException;
import util.exceptions.user.TakenNicknameException;
import util.exceptions.user.TakenUsernameException;

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

    private UserServiceImpl userService = new UserServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        signupNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!usernameNew.getText().trim().isEmpty() && !passwordNew.getText().trim().isEmpty()) {
                    try {
                        System.out.println(
                                userService.addUser(usernameNew.getText(), email.getText(), passwordNew.getText()));
                        SceneContext.user = userService.getUserByNickname(usernameNew.getText());
                        SceneContext.changeScene(event, "/fxmlFiles/login.fxml");
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText(e.getMessage());
                        alert.show();
                    }
                } else {
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
                SceneContext.changeScene(event, "/fxmlFiles/login.fxml");
            }
        });

    }
}
