package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

import models.entities.Tag;
import util.common.MyListener;

public class TagController{

    private Tag tag;
    private MyListener myListener;

    @FXML
    private Label tagLbl;

    @FXML
    private ImageView tagButton;

    @FXML
    private void tagClick(MouseEvent event) {
        myListener.tagClickListener(tag, tagButton);
    }

    public void setData(Tag tag, MyListener myListener){
        this.tag = tag;
        this.myListener = myListener;
        tagLbl.setText(tag.getName());
    }

}
