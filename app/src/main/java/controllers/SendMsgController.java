package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.entities.Recipe;
import models.entities.User;
import services.impl.UserServiceImpl;
import util.common.SceneContext;
import util.constants.FailMessages;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class SendMsgController implements Initializable {
    private UserServiceImpl userService = new UserServiceImpl();
    private User user = SceneContext.getUser();
    private Recipe recipe;

    @FXML
    private Button close;

    @FXML
    private TextField nicknameTxtField;

    @FXML
    private Button send;

    @FXML
    private TextArea msgArea;


    public void setData(Recipe recipe){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (msgArea.getText().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(String.format(FailMessages.MESSAGE_INVALID_TEXT_LENGTH));
                    alert.show();

                } else {
                    userService.sendMessage(UUID.randomUUID(), user.getId(), userService.getUserByNickname(nicknameTxtField.getText()).getId(),  msgArea.getText());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Message sent successfully");
                    alert.show();
                }

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
