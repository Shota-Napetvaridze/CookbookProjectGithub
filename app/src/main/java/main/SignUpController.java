package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button signupnew;


    @FXML
    private Button button_login;


    @FXML
    private TextField usernamenew;


    @FXML
    private TextField email;

    @FXML
    private TextField passwordnew;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        signupnew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!usernamenew.getText().trim().isEmpty() && !passwordnew.getText().trim().isEmpty()){
                    DBUtils.signUpUser(event, usernamenew.getText(), email.getText(), passwordnew.getText());
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
                DBUtils.changeScene(event, "/main/login.fxml", null, null);
            }
        });

    }
}
