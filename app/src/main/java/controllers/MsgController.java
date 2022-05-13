package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import models.entities.Message;
import services.impl.UserServiceImpl;


public class MsgController implements Initializable{

    @FXML
    private Label MsgUserLbl;

    @FXML
    private Label messageLbl;

    @FXML
    private Button removeMsg;

    @FXML
    private Button reply;


    private UserServiceImpl userService = new UserServiceImpl();
    private Message message;




    public void setData(Message message){
        this.message = message;
        messageLbl.setText(message.getText());
        MsgUserLbl.setText(userService.getUserById(message.getSender()).getNickname());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        removeMsg.setStyle("-fx-background-color: rgb(254, 215, 0)");
        removeMsg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                userService.removeMessageById(message.getId());
                // TODO: Refresh message list in HomeController
            }
        });
    }
}