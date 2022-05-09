package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import main.MyListener;
import model.Tag;

import java.net.URL;
import java.util.ResourceBundle;

public class TagController implements Initializable{

    private Tag tag;
    private MyListener myListener;

    @FXML
    private Label tagLbl;

    @FXML
    private Button tagButton;


    @FXML
    private void tagClick(MouseEvent event) {
        myListener.tagClickListener(tag, tagButton);
    }




    public void setData(Tag tag, MyListener myListener){
        this.tag = tag;
        this.myListener = myListener;
        tagLbl.setText(tag.getName());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tagButton.setShape(new Circle(1.5));
        tagButton.setMinWidth(20);
        tagButton.setMaxWidth(20);
        tagButton.setMinHeight(20);
        tagButton.setMaxHeight(20);
    }
}
