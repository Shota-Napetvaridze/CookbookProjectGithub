package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import javafx.scene.input.MouseEvent;
import main.MyListener;
import model.Message;
import model.Recipe;

public class MsgController {


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
}
