package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import models.entities.Message;
import services.impl.UserServiceImpl;
import util.common.MyListener;


public class MsgController implements Initializable{
    private UserServiceImpl userService = new UserServiceImpl();
    private MyListener myListener;
    private Message message;



    @FXML
    private Label MsgUserLbl;

    @FXML
    private TextArea messageReceivedArea;

    @FXML
    private TextArea messageSendArea;

    @FXML
    private Button removeMsg;

    @FXML
    private Button reply;

    @FXML
    void reply(MouseEvent event) {
        myListener.replyMsgListener(message);
    }




    public void setData(Message message, MyListener mylistener){
        this.message = message;
        this.myListener = myListener;
        messageReceivedArea.setText(message.getText());
        MsgUserLbl.setText(userService.getUserById(message.getSender()).getNickname());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        removeMsg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                userService.removeMessageById(message.getId());
                // TODO: Refresh message list in HomeController
            }
        });
    }
}