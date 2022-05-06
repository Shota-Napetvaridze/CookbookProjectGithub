package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import main.DBUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class NewRecipeController {

    @FXML
    private Button add_image;

    @FXML
    private Button add_recipe;


    @FXML
    private ImageView lbl_image;



    @FXML
    private TextField txt_filename;

    @FXML
    private TextField txt_recipe_name;


}
