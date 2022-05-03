package cookbook.util.common;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import cookbook.util.constants.SqlQueries;

public class DBUtil {
    private int port;
    private String user;
    private String pass;
    private Connection conn;
    private Statement stmt;

    public DBUtil(int port, String user, String pass) {
        this.port = port;
        this.user = user;
        this.pass = pass;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/",
                    user, pass);
            stmt = conn.createStatement();
        } catch (SQLException e) {
        }
    }

    public String useDatabase(String dbName) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(SqlQueries.useDatabase);
        preparedStatement.setString(1, dbName);
        preparedStatement.execute();
        return "Using database ...";
    }

    public String createDatabase(String dbName) {
        try {
            return useDatabase(dbName);
        } catch (SQLException e) {
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(SqlQueries.createDatabase);
                preparedStatement.setString(1, dbName);
                preparedStatement.execute();
                conn = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/"
                        + dbName, user, pass);
                stmt = conn.createStatement();
                try {
                    createTables();
                    useDatabase(dbName);
                    return "Using database ...";
                } catch (SQLException ex) {
                    return "Failed to create tables ...";
                }
            } catch (SQLException exe) {
                return "Failed to create database ...";
            }
        }
    }

    public void createTables() throws SQLException {
        stmt.execute(SqlQueries.createTableTags);
        stmt.execute(SqlQueries.createTableUsers);
        stmt.execute(SqlQueries.createTableMessages);
        stmt.execute(SqlQueries.createTableIngredients);
        stmt.execute(SqlQueries.createTableUserIngredients);
        stmt.execute(SqlQueries.createTableRecipes);
        stmt.execute(SqlQueries.createTableComments);
        stmt.execute(SqlQueries.createTableRecipeIngredients);
        stmt.execute(SqlQueries.createTableRecipeTags);
        stmt.execute(SqlQueries.createTableUsersFavorites);
        stmt.execute(SqlQueries.createTableWeeklyPlans);
        stmt.execute(SqlQueries.createTableUsersRecipeTags);
    }

    public void importRecords() {
        importUsers();
        importRecipes();
    }

    private void importRecipes() {
        // TODO: Create a folder "resources" in folder "app" to avoid using "src" ?
        File recipesFile = new File("src/main/java/cookbook/util/resources/data/recipes.csv");
        String recipesPath = recipesFile.getAbsolutePath();
        ArrayList<String[]> recipes = FileIo.readFromFileSaveToArrayList(recipesPath);

        // InputStream picture;
        // try {
        // picture = new FileInputStream("app/src/test/java/cookbook/recipe.png");
        // } catch (FileNotFoundException e) {
        // }

        for (String[] recipe : recipes) {
            try {

                byte[] byteData = recipe[1].getBytes("UTF-8");// Better to specify encoding
                Blob blobData = conn.createBlob();
                blobData.setBytes(1, byteData);

                PreparedStatement ps = conn.prepareStatement(SqlQueries.addRecipe);

                ps.setString(1, UUID.randomUUID().toString());
                ps.setString(2, recipe[0]);
                ps.setBytes(3, byteData);
                ps.setString(4, recipe[2]);
                ps.setString(5, recipe[4]);
                ps.setString(6, "DEFAULT");

                ps.execute();
            } catch (SQLException exept) {
                System.out.println("Could not add recipe.");
            } catch (UnsupportedEncodingException e) {
                System.out.println("Picture encoding unsupported.");
            }
        }

    }

    private void importUsers() {
        File usersFile = new File("src/main/java/cookbook/util/resources/data/users.csv");
        String usersPath = usersFile.getAbsolutePath();
        ArrayList<String[]> users = FileIo.readFromFileSaveToArrayList(usersPath);

        for (String[] user : users) {
            try {
                PreparedStatement ps = conn.prepareStatement(SqlQueries.addUser);
                ps.setString(1, UUID.randomUUID().toString());
                ps.setString(2, user[0]);
                // ps.setBlob(3, (Blob) recipe[1]); TODO:
                ps.setString(4, user[2]);
                ps.setString(5, user[4]);
                ps.setString(6, "DEFAULT");

                ps.execute();
            } catch (SQLException exept) {
                exept.printStackTrace();
            }
        }
    }

    public static void changeScene(ActionEvent event, String fxmlFIle, String username, String email) {
        Parent root = null;

        if (username != null && email != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtil.class.getResource(fxmlFIle));
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBUtil.class.getResource(fxmlFIle));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1315.0, 810.0));
        String s = String.format("Welcome to Cookbook %s", username.toUpperCase());
        stage.setTitle(s);
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String username, String email, String password) {
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
            } else {
                psInsert = connection.prepareStatement(
                        "INSERT INTO Users (Id, Username, DisplayName, Email,  Password) VALUES (?, ?, ?, ?, ?)");
                UUID uuid = UUID.randomUUID();
                String randomIdString = uuid.toString().substring(0, 6);
                psInsert.setString(1, randomIdString);
                psInsert.setString(2, username);
                psInsert.setString(3, "NULL");
                psInsert.setString(4, email);
                psInsert.setString(5, password);
                psInsert.executeUpdate();
                changeScene(event, "home2.fxml", username, email);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:8889/Cookbook", "root", "root");
            preparedStatement = connection.prepareStatement("SELECT password FROM Users WHERE Username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided Credentials are incorrect");
                alert.show();
            } else {
                // password comparison
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("Password");

                    if (retrievedPassword.equals(password)) {
                        changeScene(event, "home2.fxml", username, null);
                    } else {
                        System.out.println("Passwords did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided Credentials are incorrect");
                        alert.show();
                    }
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
