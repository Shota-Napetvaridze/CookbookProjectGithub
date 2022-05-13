package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import util.common.DbContext;
import util.constants.Variables;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, InterruptedException {

        //
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxmlFiles/login.fxml"));
        Scene loginScene = new Scene(fxmlLoader.load(), 600 , 400 ); // TODO: 600 400 (Replace) 1315.0, 810.0
        stage.setTitle("CookBook!");
        stage.setResizable(false);
        stage.setScene(loginScene);
        stage.show();
    }


        public static void main(String[] args) throws IOException, InterruptedException{
            // Start MAMP server Automatically
            // Runtime.getRuntime().exec("open /Applications/MAMP/MAMP.app/");
            // Runtime.getRuntime().exec("/Applications/MAMP/bin/startApache.sh");
            // Runtime.getRuntime().exec("/Applications/MAMP/bin/startMysql.sh");
            // TimeUnit.SECONDS.sleep(5);


            //---------------------------------------------------------------
            DbContext dbContext = new DbContext(Variables.DATABASE_PORT, Variables.DATABASE_USER, Variables.DATABASE_PASS);
            dbContext.createDatabase();
        //  Launch the cookbook app
        launch();
    }
}




