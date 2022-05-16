package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import models.entities.Message;
import models.entities.User;
import services.impl.UserServiceImpl;
import util.common.MyListener;

public class UserController {
    private UserServiceImpl userService = new UserServiceImpl();
    private MyListener myListener;
    private User user;

    @FXML
    private Button editUser;

    @FXML
    private Label emailLbl;

    @FXML
    private Label nicknameLbl;

    @FXML
    private Label passwordLbl;

    @FXML
    private Button removeUser;

    @FXML
    private Label userNameLbl;

    @FXML
    void edit(MouseEvent event) {

    }

    @FXML
    void remove(MouseEvent event) {

    }

    public void setData(User user, MyListener mylistener){
        this.user = user;
        this.myListener = myListener;
    }
}