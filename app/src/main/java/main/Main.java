package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import util.common.DbContext;
import util.constants.Variables;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxmlFiles/home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("CookBook!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


        public static void main(String[] args) {
            DbContext dbContext = new DbContext(Variables.DATABASE_PORT, Variables.DATABASE_USER, Variables.DATABASE_PASS);
            dbContext.createDatabase();

        //  Launch the cookbook app
        launch();
    }
}




