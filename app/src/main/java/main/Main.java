package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/main/fxmlFiles/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("CookBook!");
        stage.setScene(scene);
        stage.show();
    }

        public static void main(String[] args) {
            final String DB_URL = "jdbc:mysql://localhost:8889/"; // MAMP SERVER
            final String USER = "root";
            final String PASS = "root";
            DBUtils database = new DBUtils(DB_URL, USER, PASS);
            database.createDatabase();

        //  Launch the cookbook app
        launch();
    }
}




