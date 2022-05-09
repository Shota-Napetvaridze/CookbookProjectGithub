
package controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.DBUtils;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class NewRecipeController implements Initializable {

    @FXML
    private Button add_image;

    @FXML
    private Button add_recipe;

    @FXML
    private ImageView newRecipeImg;

    @FXML
    private TextArea recipe_description;

    @FXML
    private TextArea recipe_instruction;

    @FXML
    private TextField recipe_name;

    @FXML
    private TextField txt_filename;

    private File file;
    private Image image;

    private Stage createStage() {
        VBox vBox = new VBox(new Label("A Label"));
        Scene scene = new Scene(vBox);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Images");
        return stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        add_image.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                // Set extension filter
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
                fileChooser.getExtensionFilters().add(extFilter);
                file = fileChooser.showOpenDialog(createStage());
                if (file != null) {
                    txt_filename.setText(file.getAbsoluteFile().toString());
                    image = new Image(file.toURI().toString(), 100, 150, true, true);//path, prefWidth, prefHeight, preserveRatio, smooth
                    newRecipeImg.setImage(image);
                    newRecipeImg.setPreserveRatio(true);

                }
            }
        });

        add_recipe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.importUserRecipe(event, recipe_name.getText(), recipe_description.getText(), recipe_instruction.getText(), file);
                DBUtils.changeScene(event, "/fxmlFiles/home.fxml",  null, null);
            }

        });
    }


}