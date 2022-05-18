package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import models.entities.Message;
import models.entities.User;
import services.impl.UserServiceImpl;
import util.common.SceneContext;
import util.common.UserListener;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class ReplyController implements Initializable {
    private UserServiceImpl userService = new UserServiceImpl();
    private UserListener userListener;


    @FXML
    private Button close;

    @FXML
    private TextArea replyMsgArea;

    @FXML
    private Button send;

    @FXML
    private Label sender;

    @FXML
    void closeMsgReply(MouseEvent event) {
        userListener.closeMsgListener();
    }


    public void setData(UUID senderId, UserListener userListener){
        this.userListener = userListener;
        String senderNickname = userService.getUserById(senderId).getNickname();
        sender.setText(senderNickname);
        replyMsgArea.setPromptText("");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {



            }
        });

        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                HomeController homeController = new HomeController();
                homeController.initializeMsgGrid();
//                SceneContext.changeScene(event, "/fxmlFiles/login.fxml");


            }
        });

    }
}
