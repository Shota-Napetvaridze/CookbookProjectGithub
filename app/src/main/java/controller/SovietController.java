package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class SovietController implements Initializable {

    @FXML
    private ImageView lenSta;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("main/img/lenin-stalin.png");
        lenSta.setImage(image);
    }
}
