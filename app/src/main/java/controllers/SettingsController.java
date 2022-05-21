package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {


    @FXML
    private Button close;

    @FXML
    private TextField emailTxtField;

    @FXML
    private TextField newPasswordTxtField;

    @FXML
    private TextField nicknameTxtField;

    @FXML
    private TextField previousPasswordTxtField;

    @FXML
    private Button save;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
