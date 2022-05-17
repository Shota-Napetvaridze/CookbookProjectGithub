package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import models.entities.Message;
import services.impl.UserServiceImpl;
import util.common.UserListener;

import java.net.URL;
import java.util.ResourceBundle;


public class MsgController implements Initializable {
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}