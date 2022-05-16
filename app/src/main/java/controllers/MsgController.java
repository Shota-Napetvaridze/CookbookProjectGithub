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
import javafx.scene.input.TouchEvent;
import models.entities.Message;
import services.impl.UserServiceImpl;
import util.common.MyListener;
import util.common.SceneContext;


public class MsgController{
    private UserServiceImpl userService = new UserServiceImpl();
    private Message message;
    private MyListener myListener;


    @FXML
    private Label MsgUserLbl;

    @FXML
    private TextArea messageReceivedArea;

    @FXML
    private TextArea messageSendArea;


    @FXML
    void reply(MouseEvent event) {

        myListener.replyMsgListener(message);
    }
    @FXML
    void removeMsg(MouseEvent event) {
        myListener.removeMsgListener(message);
    }

    public void setData(Message message, MyListener mylistener){
        this.message = message;
        this.myListener = mylistener;
        messageReceivedArea.setText(message.getText());
        MsgUserLbl.setText(userService.getUserById(message.getSender()).getNickname());
    }

}