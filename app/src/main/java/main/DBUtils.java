package main;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;


public class DBUtils {
    private String DB_URL;
    private String USER;
    private String PASS;


    public DBUtils(String DB_URL, String USER, String PASS){
        this.DB_URL = DB_URL;
        this.USER = USER;
        this.PASS = PASS;
    }

    public void createDatabase(){
        File usersFile = new File("app/src/main/resources/main/data/users.csv");
        File recipesFile = new File("app/src/main/resources/main/data/recipes.csv");
        File tagsFile = new File("app/src/main/resources/main/data/tags.csv");
        File messagesFile = new File("app/src/main/resources/main/data/messages.csv");
        File commentsFile = new File("app/src/main/resources/main/data/comments.csv");
        File ingredientUnitsFile = new File("app/src/main/resources/main/data/ingredientUnits.csv");

        String usersPath = usersFile.getAbsolutePath();
        String recipesPath = recipesFile.getAbsolutePath();
        String tagsPath = tagsFile.getAbsolutePath();
        String messagesPath = messagesFile.getAbsolutePath();
        String commentsPath = commentsFile.getAbsolutePath();
        String ingredientUnitsPath = ingredientUnitsFile.getAbsolutePath();

        ArrayList<String[]> users = FileIo.readFromFileSaveToArrayList(usersPath);
        ArrayList<String[]> recipes = FileIo.readFromFileSaveToArrayList(recipesPath);
        ArrayList<String[]> tags = FileIo.readFromFileSaveToArrayList(tagsPath);
        ArrayList<String[]> messages = FileIo.readFromFileSaveToArrayList(messagesPath);
        ArrayList<String[]> comments = FileIo.readFromFileSaveToArrayList(commentsPath);
        ArrayList<String[]> ingredientUnits = FileIo.readFromFileSaveToArrayList(ingredientUnitsPath);

        //----------------------------------------------------------------------
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
                    Statement stmt2 = conn.createStatement();
                    // Creating a tables
                    try {
                        stmt2.execute("USE Cookbook");
                        String createTableTags = "CREATE TABLE Tags" +
                                "(Id CHAR(38) NOT NULL," +
                                "TagName VARCHAR(20) NOT NULL," +
                                "PRIMARY KEY (Id))";

                        String createTableUsers = "CREATE TABLE Users " +
                                "(Id CHAR(38) NOT NULL," +
                                "Username VARCHAR(30) NOT NULL," +
                                "DisplayName VARCHAR(30), " +
                                "Email VARCHAR(50) NOT NULL," +
                                "Password VARCHAR(64) NOT NULL," +
                                "PRIMARY KEY (Id))";

                        String createTableMessages = "CREATE TABLE Messages (" +
                                "Id CHAR(38) NOT NULL PRIMARY KEY, " +
                                "SenderId CHAR(38) NOT NULL REFERENCES Users(Id), " +
                                "ReceiverId CHAR(38) NOT NULL REFERENCES Users(Id), " +
                                "MessageText VARCHAR(300) NOT NULL," +
                                "IsRead BOOL NOT NULL )";

                        String createTableIngredients = "CREATE TABLE Ingredients (" +
                                "Id CHAR(38) NOT NULL PRIMARY KEY, " +
                                "IngredientName VARCHAR(20) NOT NULL, " +
                                "UNIT VARCHAR(20) NOT NULL )";

                        String createTableUserIngredients = "CREATE TABLE UsersIngredients (\n" +
                                "UserId CHAR(38) NOT NULL REFERENCES Users(Id),\n" +
                                "IngredientId CHAR(38) NOT NULL REFERENCES Ingredients(Id),\n" +
                                "Quantity FLOAT NOT NULL,\n" +
                                "PRIMARY KEY (UserId, IngredientId)\n" +
                                ")";

                        String createTableRecipes = "CREATE TABLE Recipes (\n" +
                                "Id CHAR(38) NOT NULL PRIMARY KEY,\n" +
                                "RecipeName VARCHAR(30) NOT NULL,\n" +
                                "Picture BLOB NOT NULL,\n" +
                                "RecipeDescription VARCHAR(1500) NOT NULL, \n" +
                                "Instructions VARCHAR(1500) NOT NULL,\n" +
                                "AuthorId CHAR(38) NOT NULL REFERENCES Users(Id)\n" +
                                ")";

                        String createTableComments = "CREATE TABLE Comments (\n" +
                                "Id CHAR(38) NOT NULL PRIMARY KEY,\n" +
                                "UserId CHAR(38) NOT NULL REFERENCES Users(Id),\n" +
                                "RecipeId CHAR(38) NOT NULL REFERENCES Recipes(Id),\n" +
                                "CommentText VARCHAR(300) NOT NULL\n" +
                                ")";

                        String createTableRecipeIngredients = "CREATE TABLE RecipeIngredients (\n" +
                                "RecipeId CHAR(38) NOT NULL REFERENCES Recipes(Id),\n" +
                                "IngredientId CHAR(38) NOT NULL REFERENCES Ingredients(Id),\n" +
                                "Quantity FLOAT NOT NULL,\n" +
                                "PRIMARY KEY (RecipeId, IngredientId)\n" +
                                ")";

                        String createTableRecipeTags = "CREATE TABLE RecipeTags (\n" +
                                "RecipeId CHAR(38) NOT NULL REFERENCES Recipes(Id),\n" +
                                "TagId CHAR(38) NOT NULL REFERENCES Tags(Id),\n" +
                                "PRIMARY KEY(RecipeId, TagId)\n" +
                                ")";
                        String createTableUsersFavorites = "CREATE TABLE UsersFavorites (\n" +
                                "UserId CHAR(38) NOT NULL REFERENCES Users(Id),\n" +
                                "RecipeId CHAR(38) NOT NULL REFERENCES Recipes(Id),\n" +
                                "PRIMARY KEY (UserId, RecipeId)\n" +
                                ")";
                        String createTableWeeklyPlans = "CREATE TABLE WeeklyPlans (\n" +
                                "UserId CHAR(38) NOT NULL REFERENCES Users(Id),\n" +
                                "RecipeId CHAR(38) REFERENCES Recipes(Id),\n" +
                                "Day BIT NOT NULL,\n" +
                                "PRIMARY KEY (UserId, RecipeId)\n" +
                                ")";

                        stmt2.execute(createTableTags);
                        stmt2.execute(createTableUsers);
                        stmt2.execute(createTableMessages);
                        stmt2.execute(createTableIngredients);
                        stmt2.execute(createTableUserIngredients);
                        stmt2.execute(createTableRecipes);
                        stmt2.execute(createTableComments);
                        stmt2.execute(createTableRecipeIngredients);
                        stmt2.execute(createTableRecipeTags);
                        stmt2.execute(createTableUsersFavorites);
                        stmt2.execute(createTableWeeklyPlans);

                        System.out.println("Tables created successfully...");
                        //---------------------------------------------------------------------------------------------------
                        try {
                            String useDb = "USE Cookbook";
                            stmt.execute(useDb);
                            //ADD SAMPLE USERS
                            for (String[] u : users) {
                                try {
                                    String importUsers = "INSERT INTO Users (\n" +
                                            "Id, Username, DisplayName, Email, Password) \n" +
                                            "VALUES (?, ?, ?, ?, ?)";
                                    PreparedStatement ps = conn.prepareStatement(importUsers);
                                    ps.setString(1, u[0]);
                                    ps.setString(2, u[1]);
                                    ps.setString(3, u[2]);
                                    ps.setString(4, u[3]);
                                    ps.setString(5, u[4]);

                                    ps.execute();
                                } catch (SQLException e7) {
                                    e7.printStackTrace();
                                }
                            }

                            //ADD SAMPLE RECIPES
                            for (String[] r : recipes) {
                                UUID uuid = UUID.randomUUID();
                                try {
                                    String importRecipes = "INSERT INTO Recipes (\n" +
                                            "Id, RecipeName, Picture, RecipeDescription, Instructions, AuthorId) \n" +
                                            "VALUES (?, ?, ?, ?, ?, ?)";
                                    PreparedStatement ps = conn.prepareStatement(importRecipes);
                                    ps.setString(1, uuid.toString());
                                    ps.setString(2, r[0]);
                                    ps.setString(3, "NULL");
                                    ps.setString(4, r[2]);
                                    ps.setString(5, r[4]);
                                    ps.setString(6, r[5]);

                                    ps.execute();
                                } catch (SQLException e6) {
                                    e6.printStackTrace();
                                }
                            }

                            //ADD SAMPLE TAGS
                            for (String[] t : tags) {
                                UUID uuid = UUID.randomUUID();
                                try {
                                    String importTags = "INSERT INTO Tags (\n" +
                                            "Id, TagName) \n" +
                                            "VALUES (?, ?)";
                                    PreparedStatement ps = conn.prepareStatement(importTags);
                                    ps.setString(1, uuid.toString());
                                    ps.setString(2, t[0]);

                                    ps.execute();
                                } catch (SQLException e5) {
                                    e.printStackTrace();
                                }
                            }

                            //ADD SAMPLE MESSAGES
                            for (String[] m : messages) {
                                UUID uuid = UUID.randomUUID();
                                try {
                                    String importMessages = "INSERT INTO Messages (\n" +
                                            "Id, SenderId, ReceiverId, MessageText, IsRead) \n" +
                                            "VALUES (?, ?, ?, ?, ?)";
                                    PreparedStatement ps = conn.prepareStatement(importMessages);
                                    ps.setString(1, uuid.toString());
                                    ps.setString(2, m[0]);
                                    ps.setString(3, m[1]);
                                    ps.setString(4, m[2]);
                                    ps.setString(5, m[3]);

                                    ps.execute();
                                } catch (SQLException e4) {
                                    e4.printStackTrace();
                                }
                            }

                            //ADD SAMPLE COMMENTS
                            for (String[] c : comments) {
                                UUID uuid = UUID.randomUUID();
                                try {
                                    String importComments = "INSERT INTO Comments (\n" +
                                            "Id, UserId, RecipeId, CommentText) \n" +
                                            "VALUES (?, ?, ?, ?)";
                                    PreparedStatement ps = conn.prepareStatement(importComments);
                                    ps.setString(1, uuid.toString());
                                    ps.setString(2, c[0]);
                                    ps.setString(3, c[1]);
                                    ps.setString(4, c[2]);

                                    ps.execute();
                                } catch (SQLException e3) {
                                    e3.printStackTrace();
                                }
                            }

                            //ADD SAMPLE INGREDIENTS + UNITS
                            for (String[] i : ingredientUnits) {
                                UUID uuid = UUID.randomUUID();
                                try {
                                    String importIngredientUnits = "INSERT INTO Ingredients (\n" +
                                            "Id, IngredientName, UNIT) \n" +
                                            "VALUES (?, ?, ?)";
                                    PreparedStatement ps = conn.prepareStatement(importIngredientUnits);
                                    ps.setString(1, uuid.toString());
                                    ps.setString(2, i[0]);
                                    ps.setString(3, i[1]);

                                    ps.execute();
                                } catch (SQLException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            System.out.println("Data inserted successfully...");

                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        //----------------------------------------------------------------------------------------------------
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

    }

    public static void changeScene(ActionEvent event, String fxmlFIle, String username, String email){
        Parent root = null;

        if (username != null && email != null){
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFIle));
                root = loader.load();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFIle));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (fxmlFIle.equals("/main/fxmlFiles/login.fxml") || fxmlFIle.equals("/main/fxmlFiles/signUp.fxml")){
            stage.setScene(new Scene(root, 600,400 ));
            String s = String.format("Welcome to Cookbook");
            stage.setTitle(s);
            stage.show();
        } else {
            stage.setScene(new Scene(root, 1315.0,810.0 ));
            String s = String.format("Welcome to Cookbook %s", username.toUpperCase());
            stage.setTitle(s);
            stage.show();
        }

    }

    public static void signUpUser(ActionEvent event, String username, String email, String password){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Cookbook", "root", "root");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM Users WHERE Username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();


            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username");
                alert.show();
            }else {
                psInsert = connection.prepareStatement("INSERT INTO Users (Id, Username, DisplayName, Email,  Password) VALUES (?, ?, ?, ?, ?)");
                UUID uuid = UUID.randomUUID();
                String randomIdString = uuid.toString().substring(0, 6);
                psInsert.setString(1, randomIdString);
                psInsert.setString(2, username);
                psInsert.setString(3, "NULL");
                psInsert.setString(4, email);
                psInsert.setString(5, password);
                psInsert.executeUpdate();
                changeScene(event, "fxmlFiles/home.fxml", username, email);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null){
                try {
                    psCheckUserExists.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psInsert != null){
                try {
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }

    public static void logInUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;


        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Cookbook", "root", "root");
            preparedStatement = connection.prepareStatement("SELECT password FROM Users WHERE Username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()){
                System.out.println("User not found!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided Credentials are incorrect");
                alert.show();
            }else {
                // password comparison
                while (resultSet.next()){
                    String retrievedPassword = resultSet.getString("Password");
                    if (retrievedPassword.equals(password)){
                        changeScene(event, "fxmlFiles/home.fxml", username, null);
                    }else {
                        System.out.println("Passwords did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided Credentials are incorrect");
                        alert.show();
                    }
                }

            }
        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }


    public static void addToFavorites(String recipeId, String userId){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckRecipeExists = null;
        ResultSet resultSet = null;


        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Cookbook", "root", "root");
            psCheckRecipeExists = connection.prepareStatement("SELECT * FROM UsersFavorites WHERE RecipeId = ?");
            psCheckRecipeExists.setString(1, recipeId);
            resultSet = psCheckRecipeExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("You already have this recipe in Favorites");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The recipe has already been added!");
                alert.show();
            }else {
                psInsert = connection.prepareStatement("INSERT INTO UsersFavorites (UserId, RecipeId) VALUES (?, ?)");
                psInsert.setString(1, "retrievedUserId"); //TODO: We need to set The UserId here
                psInsert.setString(2, recipeId);
                psInsert.executeUpdate();
            }


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheckRecipeExists != null){
                try {
                    psCheckRecipeExists.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psInsert != null){
                try {
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void removeMessage(String msgId){
        Connection connection = null;
        PreparedStatement deleteMessage = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Cookbook", "root", "root");
            deleteMessage = connection.prepareStatement("DELETE FROM Messages WHERE Messages.Id = ?");
            deleteMessage.setString(1, msgId); //TODO: We need to set The Messages.Id here
            resultSet = deleteMessage.executeQuery();


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (deleteMessage != null){
                try {
                    deleteMessage.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void searchRecipe(ActionEvent event, String recipe){
        Connection connection = null;
        PreparedStatement psCheckRecipeExists = null;
        ResultSet resultSet = null;


        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Cookbook", "root", "root");
            psCheckRecipeExists = connection.prepareStatement("SELECT * FROM Recipes WHERE RecipeName = ?");
            psCheckRecipeExists.setString(1, recipe);
            resultSet = psCheckRecipeExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("You already have this recipe in Favorites");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("The recipe has already been added!");
                alert.show();
            }else {
                // retrievedData------------------------------------------
                String retrievedData = resultSet.getString("*");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheckRecipeExists != null){
                try {
                    psCheckRecipeExists.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
