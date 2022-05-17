package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import models.entities.Comment;
import util.common.MyListener;

public class CommentController {
    private Comment comment;
    private MyListener myListener;

    @FXML
    private Label MsgUserLbl;

    @FXML
    private TextArea commentArea;

    @FXML
    void editComment(MouseEvent event) {

    }

    @FXML
    void removeComment(MouseEvent event) {

    }

    public void setData(Comment comment, MyListener myListener){
        this.comment = comment;
        this.myListener = myListener;
        commentArea.setText(comment.getText());
    }
}
