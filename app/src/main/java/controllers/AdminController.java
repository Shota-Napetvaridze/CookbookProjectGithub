package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import models.entities.Message;
import models.entities.Recipe;
import models.entities.User;
import services.impl.IngredientServiceImpl;
import services.impl.UserServiceImpl;
import util.common.MyListener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class AdminController implements Initializable {
    private UserServiceImpl userService = new UserServiceImpl();
    private MyListener myListener;
    private User user;

    @FXML
    private Label CookBookLbl;

    @FXML
    private VBox Vbox;

    @FXML
    private Button addUser;

    @FXML
    private GridPane grid;

    @FXML
    private Button logout;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Button search;

    @FXML
    private TextField searchField;




    private List<User> usersList = new ArrayList<>();
    private void initializeUsersGrid() {
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < usersList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxmlFiles/user.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                UserController userController = fxmlLoader.getController();
                userController.setData(usersList.get(i), myListener);
                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row);
                // Set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                // Set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
