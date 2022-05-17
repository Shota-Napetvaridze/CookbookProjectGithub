package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import models.entities.Tag;
import util.common.UserListener;

public class TagController{

    private Tag tag;
    private UserListener userListener;

    @FXML
    private Label tagLbl;

    @FXML
    private ImageView tagButton;

    @FXML
    private void tagClick(MouseEvent event) {
        userListener.tagClickListener(tag, tagButton);
    }

    public void setData(Tag tag, UserListener userListener){
        this.tag = tag;
        this.userListener = userListener;
        tagLbl.setText(tag.getName());
    }

}
