package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;





public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/main/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("CookBook!");
        stage.setScene(scene);
        stage.show();
    }


        public static void main(String[] args) {
            final String DB_URL = "jdbc:mysql://localhost:8889/"; // MAMP SERVER
            final String USER = "root";
            final String PASS = "root";
            try {
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();


                // Creating a database
                try {
                    stmt.execute("USE Cookbook");
                    System.out.println("Using the Cookbook Database");
                }catch (SQLException e){
                    stmt.execute("CREATE DATABASE Cookbook");
                    System.out.println("Database created successfully...");
                    // Creating a Tables
                    try {
                        Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost:8889/Cookbook", USER, PASS);
                        Statement stmt2 = conn1.createStatement();
                        // Creating a tables
                        try {
                            String sql2 = "CREATE TABLE Tags" +
                                    "(Id CHAR(38) NOT NULL," +
                                    "TagName VARCHAR(20) NOT NULL," +
                                    "PRIMARY KEY (Id))";

                            String sql3 = "CREATE TABLE Users " +
                                    "(Id CHAR(38) NOT NULL," +
                                    "Username VARCHAR(30) NOT NULL," +
                                    "DisplayName VARCHAR(30), " +
                                    "Email VARCHAR(50) NOT NULL," +
                                    "CartId INT NOT NULL," +
                                    "Password VARCHAR(64) NOT NULL," +
                                    "PRIMARY KEY (Id))";
//                                    "FOREIGN KEY (CartId) REFERENCES Carts.Id)";
                            stmt2.execute(sql2);
                            stmt2.execute(sql3);
                            System.out.println("Tables created successfully...");
                        }catch (SQLException ex){
                            ex.printStackTrace();
                        }
                    } catch (SQLException exe) {
                        System.out.println("Failed to create database ...");
                        exe.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        //  Launch the cookbook app
        launch();
    }
}




