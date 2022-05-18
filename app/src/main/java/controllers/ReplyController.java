package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import models.entities.Message;
import models.entities.User;
import services.impl.UserServiceImpl;
import util.common.SceneContext;
import util.common.UserListener;
import util.constants.FailMessages;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class ReplyController implements Initializable {
    private UserServiceImpl userService = new UserServiceImpl();
    private UserListener userListener;
    private Message message;
    private UUID senderId;


    @FXML
    private Button close;

    @FXML
    private TextArea replyMsgArea;
    @FXML
    private TextArea receivedMsgArea;

    @FXML
    private Button send;

    @FXML
    private Label sender;

    @FXML
    void closeMsgReply(MouseEvent event) {
        userListener.closeMsgListener();
    }


    public void setData(Message message, UUID senderId, UserListener userListener){
        this.userListener = userListener;
        this.message = message;
        this.senderId = senderId;
        String senderNickname = userService.getUserById(senderId).getNickname();
        sender.setText(senderNickname);
        receivedMsgArea.setText(message.getText());

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (replyMsgArea.getText().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(String.format(FailMessages.MESSAGE_INVALID_TEXT_LENGTH));
                    alert.show();

                } else {
                    userService.sendMessage(UUID.randomUUID(), message.getReceiver(), message.getSender(), replyMsgArea.getText());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Message sent successfully");
                    alert.show();
                }

            }
        });
    }
}
