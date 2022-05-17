package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import models.entities.Message;
import services.impl.UserServiceImpl;
import util.common.UserListener;


public class MsgController{
    private UserServiceImpl userService = new UserServiceImpl();
    private Message message;
    private UserListener userListener;

    @FXML
    private Label MsgUserLbl;

    @FXML
    private TextArea messageReceivedArea;

    @FXML
    void reply(MouseEvent event) {
        userListener.replyMsgListener(message);
    }

    @FXML
    void removeMsg(MouseEvent event) {
        userListener.removeMsgListener(message);
    }

    public void setData(Message message, UserListener userListener){
        this.message = message;
        this.userListener = userListener;
        messageReceivedArea.setText(message.getText());
        MsgUserLbl.setText(userService.getUserById(message.getSender()).getNickname());
    }

}