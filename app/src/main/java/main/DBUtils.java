package main;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Recipe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import javax.imageio.ImageIO;





public class DBUtils {
    private String DB_URL;
    private String USER;
    private String PASS;


    public DBUtils(String DB_URL, String USER, String PASS){
        this.DB_URL = DB_URL;
        this.USER = USER;
        this.PASS = PASS;
    }

    public File[] parseRecipeImagesDirectory(File dir){
        File[] files = dir.listFiles();
        return files;
    }

    public void createDatabase(){
        File usersFile = new File("src/main/resources/data/users.csv");
        File recipesFile = new File("src/main/resources/data/recipes.csv");
        File tagsFile = new File("src/main/resources/data/tags.csv");
        File messagesFile = new File("src/main/resources/data/messages.csv");
        File commentsFile = new File("src/main/resources/data/comments.csv");
        File ingredientUnitsFile = new File("src/main/resources/data/ingredientUnits.csv");

        //File imageDirectory = new File("src/main/resources/main/recipeImages.csv");

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
                                "IngredientName VARCHAR(60) NOT NULL, " +
                                "UNIT VARCHAR(20) NOT NULL )";

                        String createTableUserIngredients = "CREATE TABLE UsersIngredients (\n" +
                                "UserId CHAR(38) NOT NULL REFERENCES Users(Id),\n" +
                                "IngredientId CHAR(38) NOT NULL REFERENCES Ingredients(Id),\n" +
                                "Quantity FLOAT NOT NULL,\n" +
                                "PRIMARY KEY (UserId, IngredientId)\n" +
                                ")";

                        String createTableRecipes = "CREATE TABLE Recipes (\n" +
                                "Id CHAR(36) NOT NULL PRIMARY KEY,\n" +
                                "RecipeName VARCHAR(100) NOT NULL,\n" +
                                "Picture LONGBLOB NOT NULL,\n" + // --------------------- changed from BLOB TO LONGBLOB
                                "RecipeDescription VARCHAR(1500) NOT NULL, \n" +
                                "Instructions VARCHAR(4000) NOT NULL,\n" +
                                "AuthorId CHAR(36) NOT NULL REFERENCES Users(Id)\n" +
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
//-------------------------------------------------- IMPORT SAMPLE DATA -----------------------------------------------------
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

                            
                            try {
                                String importRecipe1 = "INSERT INTO Recipes (\n" +
                                            "Id, RecipeName, Picture, RecipeDescription, Instructions, AuthorId) \n" +
                                            "VALUES (?, ?, ?, ?, ?, ?)";
                                PreparedStatement ps1 = conn.prepareStatement(importRecipe1);
                                InputStream fis1 = getClass().getResourceAsStream("/recipeImages/Lasagna.png");
                                ps1.setString(1, "1");
                                ps1.setString(2, "Lasagna");
                                ps1.setBlob(3, fis1);
                                ps1.setString(4, "Lasagna is a wide, flat sheet of pasta. Lasagna can refer to either the type of noodle or to the typical lasagna dish which is a dish made with several layers of lasagna sheets with sauce and other ingredients, such as meats and cheese, in between the lasagna noodles.");
                                ps1.setString(5, "STEP 1: Preheat oven to 375 degrees F (190 degrees C). Bring a large pot of lightly salted water to a boil. Add noodles and cook for 8 to 10 minutes or until al dente. drain and set aside. STEP 2:Place pork and beef in a large, deep skillet. Cook over medium high heat until evenly brown. Stir in tomato sauce, crushed tomatoes, parsley, garlic, oregano, onion, sugar, basil and salt. Simmer over medium-low heat for 30 minutes, stirring occasionally. STEP 3: In a large bowl, combine cottage cheese, eggs, Parmesan cheese, parsley, salt and pepper. Step 4: In a 9x13 inch baking dish, place 2 layers of noodles on the bottom of dish. layer 1/2 of the cheese mixture, 1/2 of the mozzarella cheese and 1/2 of the sauce. Repeat layers. STEP 5: Cover with aluminum foil and bake in preheated oven for 30 to 40 minutes. Remove foil and bake for another 5 to 10 minutes. let stand for 10 minutes before cutting. serve.");
                                ps1.setString(6, "Sample Author");
                                ps1.execute();
                                System.out.println("Sample Recipe 1 successfully imported");

                                String importRecipe2 = "INSERT INTO Recipes (\n" +
                                            "Id, RecipeName, Picture, RecipeDescription, Instructions, AuthorId) \n" +
                                            "VALUES (?, ?, ?, ?, ?, ?)";
                                PreparedStatement ps2 = conn.prepareStatement(importRecipe2);
                                InputStream fis2 = getClass().getResourceAsStream("/recipeImages/banana-pancakes.png");
                                ps2.setString(1, "2");
                                ps2.setString(2, "Banana Pancakes");
                                ps2.setBlob(3, fis2);
                                ps2.setString(4, "Turn overripe, blackened bananas into sweet and fluffy American-style pancakes. Serve with syrup and crunchy, toasted pecan nuts as a delicious brunch treat");
                                ps2.setString(5, "STEP 1:Sieve the flour, baking powder and a generous pinch of salt into a large bowl. In a separate mixing bowl, mash the very ripe bananas with a fork until smooth, then whisk in the eggs, vanilla extract and milk. Make a well in the centre of the dry ingredients, tip in the wet ingredients and swiftly whisk together to create a smooth, silky batter.STEP 2:Heat a little knob of butter in a large non-stick pan over a medium heat. Add 2-3 tbsp of the batter to the pan and cook for several minutes, or until small bubbles start appearing on the surface. Flip the pancake over and cook for 1-2 mins on the other side. Repeat with the remaining batter, keeping the pancakes warm in a low oven.STEP 3:Stack the pancakes on plates and top with the banana slices, a glug of sticky maple syrup and a handful of pecan nuts, if you like.");
                                ps2.setString(6, "Sample Author");
                                ps2.execute();
                                System.out.println("Sample Recipe 2 successfully imported");

                                String importRecipe3 = "INSERT INTO Recipes (\n" +
                                            "Id, RecipeName, Picture, RecipeDescription, Instructions, AuthorId) \n" +
                                            "VALUES (?, ?, ?, ?, ?, ?)";
                                PreparedStatement ps3 = conn.prepareStatement(importRecipe3);
                                InputStream fis3 = getClass().getResourceAsStream("/recipeImages/beef-curry.png");
                                ps3.setString(1, "3");
                                ps3.setString(2, "Beef Curry");
                                ps3.setBlob(3, fis3);
                                ps3.setString(4, "Make our easy beef curry and serve with a hunk of naan bread to mop up the delicious juices. If you prefer it less spicy, simply add less chilli powder");
                                ps3.setString(5, "STEP 1:Heat one tbsp of the oil in a casserole pot over a medium-high heat. Season the beef and fry in the pot for 5-8 mins, turning with a pair of tongs half way until evenly browned. Set aside on a plate.STEP 2:Heat the remaining oil and butter in the pan and add the onions. Fry gently for 15 mins or until golden brown and caramelised. Add the garlic, ginger, chilli, turmeric, ground coriander and cardamom and fry for two mins. Tip in the tomatoes, stock and sugar and bring to the simmer.STEP 3:Add the beef, put a lid on top of the curry and cook over a low heat for 1 ½ – 2 hrs or until the meat is tender and falling apart. Remove the lid for the last 20 minutes of cooking.STEP 4:Stir through the garam masala and cream (if using) and season to taste. Scatter over the coriander and serve with naan breads or rice.");
                                ps3.setString(6, "Sample Author");
                                ps3.execute();
                                System.out.println("Sample Recipe 3 successfully imported");

                                String importRecipe4 = "INSERT INTO Recipes (\n" +
                                            "Id, RecipeName, Picture, RecipeDescription, Instructions, AuthorId) \n" +
                                            "VALUES (?, ?, ?, ?, ?, ?)";
                                PreparedStatement ps4 = conn.prepareStatement(importRecipe4);
                                InputStream fis4 = getClass().getResourceAsStream("/recipeImages/Tiramisu.png");
                                ps4.setString(1, "4");
                                ps4.setString(2, "Tiramisu");
                                ps4.setBlob(3, fis4);
                                ps4.setString(4, "Tiramisu is an elegant and rich layered Italian dessert made with delicate ladyfinger cookies, espresso or instant espresso, mascarpone cheese, eggs, sugar, Marsala wine, rum and cocoa powder. Through the grouping of these diverse ingredients, an intense yet refined dish emerges.");
                                ps4.setString(5, "STEP 1: Put the double cream, mascarpone, marsala and golden caster sugar in a large bowl. STEP 2: Whisk until the cream and mascarpone have completely combined and have the consistency of thickly whipped cream. STEP 3: Pour the coffee into a shallow dish. Dip in a few of the sponge fingers at a time, turning for a few seconds until they are nicely soaked, but not soggy. Layer these in a dish until you’ve used half the sponge fingers, then spread over half of the creamy mixture. STEP 4: Using the coarse side of the grater, grate over most of the dark chocolate. Then repeat the layers (you should use up all the coffee), finishing with the creamy layer. STEP 5: Cover and chill for a few hours or overnight. Will keep in the fridge for up to two days. STEP 6: To serve, dust with the cocoa powder and grate over the remainder of the chocolate.");
                                ps4.setString(6, "Sample Author");
                                ps4.execute();
                                System.out.println("Sample Recipe 4 successfully imported");

                                String importRecipe5 = "INSERT INTO Recipes (\n" +
                                            "Id, RecipeName, Picture, RecipeDescription, Instructions, AuthorId) \n" +
                                            "VALUES (?, ?, ?, ?, ?, ?)";
                                PreparedStatement ps5 = conn.prepareStatement(importRecipe5);
                                InputStream fis5 = getClass().getResourceAsStream("/recipeImages/Beef-Stroganoff.png");
                                ps5.setString(1, "5");
                                ps5.setString(2, "Beef Stroganoff");
                                ps5.setBlob(3, fis5);
                                ps5.setString(4, "Beef Stroganoff is a popular Russian dish of small pieces of beef fillet sautéed in sour cream sauce together with onions and mushrooms. The dish was named after Count Alexander Grigorievich Stroganoff, who lived in the late 19th century in Odessa.");
                                ps5.setString(5, "STEP 1: Heat 1 tbsp olive oil in a non-stick frying pan then add 1 sliced onion and cook on a medium heat until completely softened, around 15 mins, adding a little splash of water if it starts to stick. STEP 2: Crush in 1 garlic clove and cook for 2-3 mins more, then add 1 tbsp butter. STEP 3: Once the butter is foaming a little, add 250g sliced mushrooms and cook for around 5 mins until completely softened. STEP 4: Season everything well, then tip onto a plate. STEP 5: Tip 1 tbsp plain flour into a bowl with a big pinch of salt and pepper, then toss 500g sliced fillet steak in the seasoned flour. STEP 6: Add the steak pieces to the pan, splashing in a little oil if the pan looks dry, and fry for 3-4 mins, until well coloured. STEP 7: Tip the onions and mushrooms back into the pan. Whisk 150g crème fraîche, 1 tsp English mustard and 100ml beef stock together, then stir into the pan. STEP 8: Cook over a medium heat for around 5 mins. STEP 9: Scatter with some chopped parsley, and serve with pappardelle or rice.");
                                ps5.setString(6, "Sample Author");
                                ps5.execute();
                                System.out.println("Sample Recipe 5 successfully imported");

                                String importRecipe6 = "INSERT INTO Recipes (\n" +
                                            "Id, RecipeName, Picture, RecipeDescription, Instructions, AuthorId) \n" +
                                            "VALUES (?, ?, ?, ?, ?, ?)";
                                PreparedStatement ps6 = conn.prepareStatement(importRecipe6);
                                InputStream fis6 = getClass().getResourceAsStream("/recipeImages/greek-roast-lamb.png");
                                ps6.setString(1, "6");
                                ps6.setString(2, "Greek Roast Lamb");
                                ps6.setBlob(3, fis6);
                                ps6.setString(4, "If spring is in the air you don't want to be slaving over a hot oven, so make the most of seasonal lamb the easy way with this lazy roast.");
                                ps6.setString(5, "STEP 1:Heat oven to 240C/fan 220C/gas 9. Pound the garlic, half the oregano, lemon zest and a pinch of salt in a pestle and mortar, then add the lemon juice and a drizzle of olive oil. Stab the lamb all over with a sharp knife, then push as much of the herb paste as you can into the holes.STEP 2:Tip the potatoes into a large roasting tin, then toss in the remaining olive oil and any remaining herb paste. Nestle the lamb amongst the potatoes, roast for 20 mins, then reduce the temperature to 180C/fan 160C/gas 4. Roast for 1 hr 15 mins for medium-rare, adding another 15 mins if you prefer your lamb medium. Baste the lamb once or twice with the juices and toss the potatoes. When the lamb is done to your liking, remove from the tin and let it rest. Throw the rest of the oregano in with the potatoes, scoop from the tin and keep warm.STEP 3:Place the roasting tin over a medium flame, add the canned tomatoes and olives to the pan juices, then simmer for a few mins. Serve the lamb with the potatoes and sauce and a simple salad.");
                                ps6.setString(6, "Sample Author");
                                ps6.execute();
                                System.out.println("Sample Recipe 6 successfully imported");

                                /*String importRecipe7 = "INSERT INTO Recipes (\n" +
                                            "Id, RecipeName, Picture, RecipeDescription, Instructions, AuthorId) \n" +
                                            "VALUES (?, ?, ?, ?, ?, ?)";
                                PreparedStatement ps7 = conn.prepareStatement(importRecipe7);
                                InputStream in7 = new FileInputStream("/main/recipeImages/<RECIPE FILE NAME.png>");
                                ps6.setString(1, "7");
                                ps6.setString(2, "Beef Curry");
                                ps6.setBlob(3, in7);
                                ps6.setString(4, "");
                                ps6.setString(5, "");
                                ps6.setString(6, "Sample Author");
                                ps6.execute();*/

                            } catch (SQLException e8) {
                                e8.printStackTrace();
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
        //SIZES OF THE DIFFERENT SCENES
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (fxmlFIle.equals("/fxmlFiles/login.fxml") || fxmlFIle.equals("/fxmlFiles/signUp.fxml") || fxmlFIle.equals("/fxmlFiles/addNewRecipe.fxml")){
            stage.setScene(new Scene(root, 600,400 ));
            String title = String.format("Welcome to Cookbook");
            stage.setTitle(title);
            stage.show();
        } else {
            stage.setScene(new Scene(root, 1315.0,810.0 ));
            stage.centerOnScreen();
            String title = String.format("Welcome to Cookbook %s", username.toUpperCase());
            stage.setTitle(title);
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
                changeScene(event, "/fxmlFiles/home.fxml", username, email);
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
                        changeScene(event, "/fxmlFiles/home.fxml", username, null);
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

    public static void addToFavorites(String recipeId, String userId, ImageView heartImage){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckRecipeExists = null;
        PreparedStatement deleteRecipe = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Cookbook", "root", "root");
            psCheckRecipeExists = connection.prepareStatement("SELECT * FROM UsersFavorites WHERE RecipeId = ?");
            psCheckRecipeExists.setString(1, recipeId);
            resultSet = psCheckRecipeExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Removed From UsersFavorites");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Would you like to remove from Favorites ?");
                alert.show();
                Image image2 = new Image("/img/heart.png");
                heartImage.setImage(image2);
                deleteRecipe = connection.prepareStatement("DELETE FROM UsersFavorites WHERE RecipeId = ?");
                deleteRecipe.setString(1, recipeId);
                deleteRecipe.executeUpdate();
            }else {
                System.out.println("Added to UsersFavorites");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Would you like to add to Favorites ?");
                alert.show();
                Image image = new Image("/img/filledWithLove.png");
                heartImage.setImage(image);
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

    public static void chosenTags(String tagName, Button tagButton){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckTagExists = null;
        PreparedStatement deleteTag = null;

        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Cookbook", "root", "root");
            psCheckTagExists = connection.prepareStatement("SELECT * FROM Tags WHERE TagName = ?");
            psCheckTagExists.setString(1, tagName);
            resultSet = psCheckTagExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                tagButton.setStyle("-fx-background-color: #FFFFFF");
                deleteTag = connection.prepareStatement("DELETE FROM Tags WHERE TagName = ?");
                deleteTag.setString(1, tagName);
                deleteTag.executeUpdate();
            }else {
                tagButton.setStyle("-fx-background-color: #FF0000");
                psInsert = connection.prepareStatement("INSERT INTO Tags (Id, TagName) VALUES (?, ?)");
                UUID uuid = UUID.randomUUID();
                String randomIdString = uuid.toString().substring(0, 6);
                psInsert.setString(1, randomIdString); //TODO: We need to set The Id here
                psInsert.setString(2, tagName);
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
            if (psCheckTagExists != null){
                try {
                    psCheckTagExists.close();
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

    public static void chosenIngredients(String ingredientName, Button tagButton){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckIngredientExists = null;
        PreparedStatement deleteIngredient = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Cookbook", "root", "root");
            psCheckIngredientExists = connection.prepareStatement("SELECT * FROM Ingredients WHERE ingredientName = ?");
            psCheckIngredientExists.setString(1, ingredientName);
            resultSet = psCheckIngredientExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                tagButton.setStyle("-fx-background-color: #FFFFFF");
                deleteIngredient = connection.prepareStatement("DELETE FROM Ingredients WHERE ingredientName = ?");
                deleteIngredient.setString(1, ingredientName);
                deleteIngredient.executeUpdate();
            }else {
                tagButton.setStyle("-fx-background-color: #FF0000");
                psInsert = connection.prepareStatement("INSERT INTO Ingredients (Id, ingredientName, UNIT) VALUES (?, ?, ?)");
                UUID uuid = UUID.randomUUID();
                String randomIdString = uuid.toString().substring(0, 6);
                psInsert.setString(1, randomIdString); //TODO: We need to set The Id here
                psInsert.setString(2, ingredientName);
                psInsert.setString(3, "NULL");
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
            if (psCheckIngredientExists != null){
                try {
                    psCheckIngredientExists.close();
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

    //IMPORT USER DEFINED RECIPES TO DATABASE
    public static void importUserRecipe(ActionEvent event, String recipeName, String recipeDescription, String recipeInstructions, File file) {
        FileInputStream fis;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Cookbook", "root", "root");
            System.out.println(file.getAbsoluteFile());

            String importUserRecipes = "INSERT INTO Recipes (\n" +
                    "Id, RecipeName, Picture, RecipeDescription, Instructions, AuthorId) \n" +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            fis = new FileInputStream(file);

            ps = conn.prepareStatement(importUserRecipes);

            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, recipeName);
            ps.setBinaryStream(3, (InputStream)fis,(int)file.length());
            ps.setString(4, recipeDescription);
            ps.setString(5, recipeInstructions);
            ps.setString(6, "SAMPLE");
            ps.execute();
            System.out.println("RECIPES IMPORTED TO HOME SUCCESSFULLY");

        } catch (SQLException | FileNotFoundException e6) {
            e6.printStackTrace();
        }
    }

    //GET RECIPES FROM DATABASE
    public static ArrayList<Recipe> getRecipesFromDB(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Recipe> recipeData = new ArrayList<Recipe>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Cookbook", "root", "root");
            ps = conn.prepareStatement("SELECT RecipeName, Picture, Id, AuthorId FROM Recipes");
            rs = ps.executeQuery();

            while (rs.next()) {
                Recipe thisRecipe = new Recipe();

                InputStream is = rs.getBinaryStream("Picture");
                Image img = new Image(is);
                thisRecipe.setName(rs.getString("RecipeName"));
                thisRecipe.setRecipeId(rs.getString("Id"));
                thisRecipe.setUserId(rs.getString("AuthorId"));
                thisRecipe.setRecipeImage(img);
                recipeData.add(thisRecipe);
            }
            System.out.println("RECIPES RETRIEVED SUCCESSFULLY");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return recipeData;   
    }

    //REMOVE RECIPE FROM HOME
    public static void removeRecipeFromHome(String Id) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Cookbook", "root", "root");
            ps = conn.prepareStatement("DELETE FROM Recipes WHERE Id = ?");
            ps.setString(1, Id);
            ps.execute();
            System.out.println("RECIPE REMOVED SUCCESSFULLY");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //GET MESSAGES FROM DATABASE
    /*public static ArrayList<Pair<String, String>> getMessagesFromDB(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Pair<String, Image>> recipeData = new ArrayList<Pair<String, Image>>();

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Cookbook", "root", "root");
            ps = conn.prepareStatement("SELECT RecipeName, Picture FROM Recipes");
            rs = ps.executeQuery();

            while (rs.next()) {
                String recipeName = rs.getString("RecipeName");
                InputStream is = rs.getBinaryStream("Picture");
                Image img = new Image(is);
                Pair<String, Image> tuple = new Pair<String, Image>(recipeName, img);
                recipeData.add(tuple);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return recipeData;   
    }*/
}
