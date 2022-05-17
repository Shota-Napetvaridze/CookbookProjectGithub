package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import models.entities.Comment;
import models.entities.User;
import services.UserService;
import services.impl.UserServiceImpl;
import util.common.SceneContext;
import util.common.UserListener;

public class CommentController {
    private Comment comment;
    private UserListener userListener;
    private User user = SceneContext.getUser();
    private UserService userService = new UserServiceImpl();
    @FXML
    private Label author;

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
        author.setText(userService.getUserById(comment.getUser()).getNickname());
        commentArea.setText(comment.getText());
        
    }
}
