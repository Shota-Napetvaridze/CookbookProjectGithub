package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import models.entities.Comment;
import util.common.UserListener;

public class CommentController {
    private Comment comment;
    private UserListener userListener;

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

    public void setData(Comment comment, UserListener userListener){
        this.comment = comment;
        this.userListener = userListener;
        commentArea.setText(comment.getText());
    }
}
