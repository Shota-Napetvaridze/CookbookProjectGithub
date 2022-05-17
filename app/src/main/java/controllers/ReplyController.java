package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import models.entities.Message;
import models.entities.User;
import services.impl.UserServiceImpl;
import util.common.UserListener;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class ReplyController implements Initializable {
    private UserServiceImpl userService = new UserServiceImpl();


    private Message message;


    @FXML
    private Button close;

    @FXML
    private TextArea replyMsgArea;

    @FXML
    private Button send;

    @FXML
    private Label sender;


    public void setData(UUID senderId){
        String senderNickname = userService.getUserById(senderId).getNickname();
        sender.setText(senderNickname);
        replyMsgArea.setPromptText("");

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
