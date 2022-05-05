package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.DBUtils;
import model.Message;

import java.net.URL;
import java.util.ResourceBundle;


public class MsgController implements Initializable {


    @FXML
    private Label MsgUserLbl;

    @FXML
    private Label messageLbl;

    @FXML
    private Button removeMsg;


    private Message message;


    public void setData(Message message){
        this.message = message;
        messageLbl.setText(message.getUserMessage());
        MsgUserLbl.setText(message.getUserName());

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        removeMsg.setStyle("-fx-background-color: rgb(254, 215, 0)");
        removeMsg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.removeMessage(message.getUserMessage());
            }
        });
    }
}
