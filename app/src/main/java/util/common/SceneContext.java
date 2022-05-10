package util.common;

import java.io.IOException;

import models.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;


public class SceneContext {
    public static void changeScene(ActionEvent event, String fxmlFile, User user) {
        Parent root = null;

        if (user != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DbContext.class.getResource(fxmlFile));
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DbContext.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1315.0, 810.0));
        String s = String.format("Welcome to Cookbook %s", user.getUsername().toUpperCase());
        stage.setTitle(s);
        stage.show();
    }

}
