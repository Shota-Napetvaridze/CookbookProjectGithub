package util.common;

import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javafx.scene.image.Image;
import models.entities.Ingredient;
import models.entities.Message;
import models.entities.Recipe;
import models.entities.Tag;
import models.entities.User;
import models.entities.enums.Unit;
import util.constants.FailMessages;
import util.constants.SqlQueries;
import util.constants.SuccessMessages;
import util.constants.Variables;
import util.exceptions.common.InvalidLengthException;
import util.exceptions.user.InvalidEmailException;
import util.exceptions.user.InvalidNicknameLengthException;
import util.exceptions.user.InvalidPasswordComplexityException;
import util.exceptions.user.InvalidPasswordLengthException;
import util.exceptions.user.InvalidUserNameLengthException;
import util.exceptions.user.TakenEmailException;
import util.exceptions.user.TakenNicknameException;
import util.exceptions.user.TakenUsernameException;

public class DbContext {
    private static Connection conn;

    public DbContext(int port, String user, String pass) {

        try {
            conn = DriverManager.getConnection(Variables.DATABASE_SERVER_URL,
                    Variables.DATABASE_USER, Variables.DATABASE_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ------------------- INIT -------------------//
    public void useDatabase() throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement(SqlQueries.setMaxAllowedPackage);
        preparedStatement.execute();
        preparedStatement = conn.prepareStatement(SqlQueries.useDatabase);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void createDatabase() {
        try {
            conn = DriverManager.getConnection(Variables.DATABASE_COOKBOOK_URL, Variables.DATABASE_USER,
                    Variables.DATABASE_PASS);
            useDatabase();
        } catch (SQLException e) {
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(SqlQueries.setMaxAllowedPackage);
                preparedStatement.execute();
                preparedStatement = conn.prepareStatement(SqlQueries.createDatabase);
                preparedStatement.execute();

                useDatabase();
                try {
                    createTables();
                    useDatabase();
                    importRecords();
                    System.out.println("CREATED DATABASE SUCCESSFULLY !!!");
                    System.out.println("USING DATABASE ....");
                } catch (SQLException ex) {
                    ex.printStackTrace(); // modified here
                    System.out.println("FAILED TO CREATE TABLES ...");
                }
            } catch (SQLException exe) {
                exe.printStackTrace(); // modified here
                System.out.println("FAILED TO CREATE DATABASE");
            }
        }
        try {
            useDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTables() throws SQLException {
        Statement stmt = conn.createStatement();
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
        stmt.close();
    }

    public void importRecords() {
        importUsers();
        importRecipes();
        importTags();
        importMessages();
        importComments();
        importIngredients();
    }

    private void importIngredients() {
        String ingredientUnitsFile = "/src/main/resources/data/ingredientUnits.csv";
        String ingredientUnitsPath = System.getProperty("user.dir") + ingredientUnitsFile;
        ArrayList<String[]> ingredientUnits = FileIo.readFromFileSaveToArrayList(ingredientUnitsPath);
        int counter = 0;
        for (String[] ingredient : ingredientUnits) {
            counter++;
            try {
                PreparedStatement ps = conn.prepareStatement(SqlQueries.addIngredient);
                ps.setString(1, ingredient[0]);
                ps.setString(2, ingredient[1]);
                ps.setString(3, ingredient[2]);

                ps.execute();
                ps.close();
                System.out.println("INGREDIENT NO " + counter + " IMPORTED SUCCESSFULLY");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void importComments() {
        String commentsFile = "/src/main/resources/data/comments.csv";
        String commentsPath = System.getProperty("user.dir") + commentsFile;
        ArrayList<String[]> comments = FileIo.readFromFileSaveToArrayList(commentsPath);
        int counter = 0;
        for (String[] comment : comments) {
            counter++;
            try {
                PreparedStatement ps = conn.prepareStatement(SqlQueries.addComment);
                ps.setString(1, comment[0]);
                ps.setString(2, comment[1]);
                ps.setString(3, comment[2]);
                ps.setString(4, comment[3]);

                ps.execute();
                ps.close();
                System.out.println("COMMENT NO " + counter + " IMPORTED SUCCESSFULLY");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void importMessages() {
        String messagesFile = "/src/main/resources/data/messages.csv";
        String messagesPath = System.getProperty("user.dir") + messagesFile;
        ArrayList<String[]> messages = FileIo.readFromFileSaveToArrayList(messagesPath);
        int counter = 0;
        for (String[] message : messages) {
            counter++;
            try {
                PreparedStatement ps = conn.prepareStatement(SqlQueries.addMessage);
                ps.setString(1, message[0]);
                ps.setString(2, message[1]);
                ps.setString(3, message[2]);
                ps.setString(4, message[3]);
                ps.setBoolean(5, Boolean.parseBoolean(message[4]));

                ps.execute();
                ps.close();
                System.out.println("MESSAGE NO " + counter + " IMPORTED SUCCESSFULLY !!!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void importTags() {
        String tagsFile = "/src/main/resources/data/tags.csv";
        String tagsPath = System.getProperty("user.dir") + tagsFile;
        ArrayList<String[]> tags = FileIo.readFromFileSaveToArrayList(tagsPath);
        int counter = 0;
        for (String[] tag : tags) {
            counter++;
            try {
                PreparedStatement ps = conn.prepareStatement(SqlQueries.addTag);
                ps.setString(1, tag[0]);
                ps.setString(2, tag[1]);

                ps.execute();
                ps.close();
                System.out.println("TAG NO " + counter + " IMPORTED SUCCESSFULLY !!!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void importRecipes() { // WORKS NOW
        String recipesFile = "/src/main/resources/data/recipes.csv";
        String recipesPath = System.getProperty("user.dir") + recipesFile;
        ArrayList<String[]> recipes = FileIo.readFromFileSaveToArrayList(recipesPath);
        int counter = 0;
        for (String[] recipe : recipes) {
            counter++;
            try {
                PreparedStatement ps = conn.prepareStatement(SqlQueries.addRecipe);
                InputStream inputStream = getClass().getResourceAsStream(recipe[6]);
                ps.setString(1, recipe[5]);
                ps.setString(2, recipe[0]);
                ps.setBlob(3, inputStream);
                ps.setString(4, recipe[2]);
                ps.setString(5, recipe[4]);
                ps.setString(6, UUID.randomUUID().toString());
                ps.execute();
                ps.close();
                System.out.println("RECIPE NO " + counter + " IMPORTED SUCCESSFULLY !!!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void importUsers() {
        String usersFile = "/src/main/resources/data/users.csv";
        String usersPath = System.getProperty("user.dir") + usersFile;
        ArrayList<String[]> users = FileIo.readFromFileSaveToArrayList(usersPath);
        int counter = 0;
        for (String[] user : users) {
            counter++;
            try {
                PreparedStatement ps = conn.prepareStatement(SqlQueries.addUser);
                ps.setString(1, user[0]);
                ps.setString(2, user[1]);
                ps.setString(3, user[2]);
                ps.setString(4, user[3]);
                ps.setString(5, user[4]);

                ps.execute();
                ps.close();
                System.out.println("USER NO" + counter + " IMPORTED SUCCESSFULLY");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // ------------------- USER -------------------//
    public boolean validateUniqueCredentials(String username, String email) throws TakenUsernameException,
            TakenEmailException, InvalidUserNameLengthException, TakenNicknameException, InvalidNicknameLengthException,
            InvalidPasswordComplexityException, InvalidPasswordLengthException, InvalidEmailException {
        if (getUserByUsername(username) != null) {
            throw new TakenUsernameException(username);
        }
        if (getUserByEmail(email) != null) {
            throw new TakenEmailException();
        }
        return true;
        // try {
        // useDatabase();
        // PreparedStatement ps =
        // conn.prepareStatement(SqlQueries.validateUniqueCredentials);
        // ps.setString(1, username);
        // ps.setString(2, email);
        // ResultSet rs = ps.executeQuery();

        // if (rs.next()) {
        // rs.close();
        // ps.close();
        // return false;
        // }
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // return true;
    }

    public String addUser(String username, String email, String password)
            throws TakenUsernameException, InvalidUserNameLengthException, TakenNicknameException,
            InvalidNicknameLengthException, InvalidPasswordComplexityException, InvalidPasswordLengthException,
            InvalidEmailException, TakenEmailException {
        try {
            UUID id = UUID.randomUUID();
            String nickname = username;
            Validator.validatePassword(password);
            String hashedPassword = Hasher.hashString(password);
            Dictionary<UUID, Integer> cart = new Hashtable<>();
            Set<UUID> messages = new HashSet<>();
            Dictionary<UUID, Date> weeklyList = new Hashtable<>();
            Set<UUID> favorites = new HashSet<>();
            Set<UUID> recipes = new HashSet<>();
            User user = new User(id, username, nickname, email, hashedPassword, cart, messages, weeklyList, favorites,
                    recipes);

            PreparedStatement ps = conn.prepareStatement(SqlQueries.addUser);
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, username);
            ps.setString(3, username);
            ps.setString(4, email);
            ps.setString(5, Hasher.hashString(password));
            ps.execute();
            ps.close();
            return String.format(SuccessMessages.USER_ADDED);
        } catch (SQLException e) {
            return String.format(FailMessages.USER_ADD_FAIL);
        } catch (NoSuchAlgorithmException e) {
            return String.format(FailMessages.INVALID_ALGORITHM);
        }
    }

    public String removeUserById(UUID userId) {
        try {
            PreparedStatement ps = conn.prepareStatement(SqlQueries.removeUserWithId);
            ps.setString(1, userId.toString());

            int result = ps.executeUpdate();
            ps.close();

            if (result != 0) {
                return String.format(SuccessMessages.USER_DELETED);
            } else {
                return String.format(FailMessages.USER_DELETE_FAIL);
            }
        } catch (SQLException e) {
            return String.format(FailMessages.USER_DELETE_FAIL);
        }
    }

    public User getUserByCredentials(String username, String password) {
        User user = null;

        try {
            useDatabase();
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getUserByCredentials);
            ps.setString(1, username);
            ps.setString(2, Hasher.hashString(password));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                UUID userId = UUID.fromString(rs.getString("id"));
                user = getUserById(userId);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUserById(UUID id) {
        User user = null;
        try {
            useDatabase();
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getUserById);
            ps.setString(1, id.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String username = rs.getString("username");
                String nickname = rs.getString("display_name");
                String email = rs.getString("email");
                String password = rs.getString("password");

                Dictionary<UUID, Integer> cart = getUserCart(id);
                Set<UUID> messages = getUserMessageIds(id);
                Dictionary<UUID, Date> weeklyList = getUserWeeklyList(id);
                Set<UUID> favorites = getUserFavorites(id);
                Set<UUID> recipes = getUserRecipes(id);

                user = new User(id, username, nickname, email, password, cart, messages, weeklyList, favorites,
                        recipes);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    private User getUserByUsername(String username) {
        User user = null;
        try {
            useDatabase();
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getUserByUsername);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String nickname = rs.getString("display_name");
                String email = rs.getString("email");
                String password = rs.getString("password");

                Dictionary<UUID, Integer> cart = getUserCart(id);
                Set<UUID> messages = getUserMessageIds(id);
                Dictionary<UUID, Date> weeklyList = getUserWeeklyList(id);
                Set<UUID> favorites = getUserFavorites(id);
                Set<UUID> recipes = getUserRecipes(id);

                user = new User(id, username, nickname, email, password, cart, messages, weeklyList, favorites,
                        recipes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUserByNickname(String nickname) {
        User user = null;
        try {
            useDatabase();
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getUserByNickname);
            ps.setString(1, nickname);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String username = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("password");

                Dictionary<UUID, Integer> cart = getUserCart(id);
                Set<UUID> messages = getUserMessageIds(id);
                Dictionary<UUID, Date> weeklyList = getUserWeeklyList(id);
                Set<UUID> favorites = getUserFavorites(id);
                Set<UUID> recipes = getUserRecipes(id);

                user = new User(id, username, nickname, email, password, cart, messages, weeklyList, favorites,
                        recipes);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    private User getUserByEmail(String email) {
        User user = null;
        try {
            useDatabase();
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getUserByEmail);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String username = rs.getString("username");
                String nickname = rs.getString("display_name");
                String password = rs.getString("password");

                Dictionary<UUID, Integer> cart = getUserCart(id);
                Set<UUID> messages = getUserMessageIds(id);
                Dictionary<UUID, Date> weeklyList = getUserWeeklyList(id);
                Set<UUID> favorites = getUserFavorites(id);
                Set<UUID> recipes = getUserRecipes(id);

                user = new User(id, username, nickname, email, password, cart, messages, weeklyList, favorites,
                        recipes);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    private Set<UUID> getUserMessageIds(UUID id) {
        Set<UUID> messages = new HashSet<>();

        try {
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getUserMessages);
            ps.setString(1, id.toString());
            ps.setString(2, id.toString());
            ResultSet user_messages = ps.executeQuery();

            while (user_messages.next()) {
                UUID messageId = UUID.fromString(user_messages.getString("id"));

                messages.add(messageId);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    public Set<User> getAllUsers() {
        Set<User> users = new HashSet<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SqlQueries.getAllUsers);

            while (rs.next()) {
                UUID userId = UUID.fromString(rs.getString("id"));
                users.add(getUserById(userId));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private static Set<UUID> getUserRecipes(UUID id) {
        Set<UUID> recipes = new HashSet<>();

        try {
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getUserRecipes);
            ps.setString(1, id.toString());
            ResultSet userRecipes = ps.executeQuery();

            while (userRecipes.next()) {
                UUID recipeId = UUID.fromString(userRecipes.getString("id"));

                recipes.add(recipeId);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipes;
    }

    private static Set<UUID> getUserFavorites(UUID id) {
        Set<UUID> favorites = new HashSet<>();

        try {
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getUserFavorites);
            ps.setString(1, id.toString());
            ResultSet userFavorites = ps.executeQuery();

            while (userFavorites.next()) {
                UUID recipeId = UUID.fromString(userFavorites.getString("recipe_id"));

                favorites.add(recipeId);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return favorites;
    }

    public boolean addRecipeToFavorites(UUID userId, UUID recipeId) {
        try {
            // useDatabase();
            PreparedStatement ps = conn.prepareStatement(SqlQueries.addRecipeFavorite);
            ps.setString(1, userId.toString());
            ps.setString(2, recipeId.toString());
            ps.execute();
            ps.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean removeRecipeFromFavorites(UUID userId, UUID recipeId) {
        try {
            PreparedStatement ps = conn.prepareStatement(SqlQueries.removeRecipeFavorite);
            ps.setString(1, userId.toString());
            ps.setString(2, recipeId.toString());
            ps.execute();
            ps.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public Dictionary<UUID, Date> getUserWeeklyList(UUID id) {
        Dictionary<UUID, Date> weeklyList = new Hashtable<>();

        try {
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getUserWeeklyList);
            ps.setString(1, id.toString());
            ResultSet userWeeklyList = ps.executeQuery();

            while (userWeeklyList.next()) {
                UUID recipeId = UUID.fromString(userWeeklyList.getString("recipe_id"));
                Date date = userWeeklyList.getDate("day");

                weeklyList.put(recipeId, date);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weeklyList;
    }

    public List<Message> getUserMessages(UUID id) {
        List<Message> messages = new ArrayList<>();

        try {
            useDatabase();
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getUserMessages);
            ps.setString(1, id.toString());
            ps.setString(2, id.toString());
            ResultSet user_messages = ps.executeQuery();

            while (user_messages.next()) {
                UUID messageId = UUID.fromString(user_messages.getString("id"));
                UUID senderId = UUID.fromString(user_messages.getString("sender_id"));
                UUID receiverId = UUID.fromString(user_messages.getString("receiver_id"));
                String text = user_messages.getString("message_text");
                Boolean isRead = user_messages.getBoolean("is_read");

                Message message = new Message(messageId, senderId, receiverId, text, isRead);
                messages.add(message);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }

    public static boolean checkExistId(UUID id, String tableName) {
        try {
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getInstanceById);
            ps.setString(1, tableName);
            ps.setString(2, id.toString());
            ResultSet rs = ps.executeQuery();
            ps.close();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Dictionary<UUID, Integer> getUserCart(UUID id) {
        Dictionary<UUID, Integer> cart = new Hashtable<>();

        try {
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getUserCart);
            ps.setString(1, id.toString());
            ResultSet user_ingredients = ps.executeQuery();

            while (user_ingredients.next()) {
                UUID recipeId = UUID.fromString(user_ingredients.getString("recipe_id"));
                Integer quantity = user_ingredients.getInt("quantity");
                cart.put(recipeId, quantity);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cart;
    }

    public String userChangeNickname(UUID userId, String nickname) {

        try {
            PreparedStatement ps = conn.prepareStatement(SqlQueries.updateNickname);
            ps.setString(1, nickname);
            ps.setString(2, userId.toString());

            int result = ps.executeUpdate();
            ps.close();

            if (result != 0) {
                return String.format(SuccessMessages.USER_SET_NICKNAME, nickname);
            } else {
                return String.format(FailMessages.USER_SET_NICKNAME_FAIL);
            }
        } catch (SQLException e) {
            return String.format(FailMessages.USER_SET_NICKNAME_FAIL);
        }
    }

    public String userChangeEmail(UUID userId, String email) {
        try {
            PreparedStatement ps = conn.prepareStatement(SqlQueries.updateEmail);
            ps.setString(1, email);
            ps.setString(2, userId.toString());

            int result = ps.executeUpdate();
            ps.close();

            if (result != 0) {
                return String.format(SuccessMessages.USER_SET_EMAIL, email);
            } else {
                return String.format(FailMessages.USER_SET_EMAIL_FAIL);
            }
        } catch (SQLException e) {
            return String.format(FailMessages.USER_SET_EMAIL_FAIL);
        }
    }

    public void sendMessage(UUID senderId, UUID receiverId, String message) {
        try {
            PreparedStatement ps = conn.prepareStatement(SqlQueries.addMessage);
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, senderId.toString());
            ps.setString(3, receiverId.toString());
            ps.setString(4, message);
            ps.setBoolean(5, false);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // ------------------- RECIPE -------------------//

    public List<Recipe> getAllRecipes() {
        List<Recipe> allRecipes = new ArrayList<>();
        // Recipe recipe = new Recipe();

        try {
            useDatabase();
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getAllRecipes);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                UUID recipeId = UUID.fromString(rs.getString("id"));
                Recipe recipe = getRecipeById(recipeId);

                allRecipes.add(recipe);
            }
            ps.close();
            System.out.println("RECIPES RETRIEVED SUCCESSFULLY");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allRecipes;
    }

    public List<Recipe> getFavoriteRecipes(UUID userId) {
        List<Recipe> recipes = new ArrayList<>();
        try {
            useDatabase();
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getFavoriteRecipes);
            ps.setString(1, userId.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Recipe recipe = getRecipeById(UUID.fromString(rs.getString("recipe_id")));
                recipes.add(recipe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;

    }

    public Recipe getRecipeById(UUID recipeId) {
        Recipe recipe = null;
        try {
            useDatabase();
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getRecipeById);
            ps.setString(1, recipeId.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("recipe_name");
                InputStream inputStream = rs.getBinaryStream("picture");
                Image picture = new Image(inputStream);
                String description = rs.getString("recipe_description");
                String instructions = rs.getString("instructions");
                UUID authorId = UUID.fromString(rs.getString("author_id"));
                Set<Tag> tags = getRecipeTagsById(recipeId);
                Map<Ingredient, Integer> ingredients = getRecipeIngredientsById(recipeId);
                Set<UUID> comments = getRecipeCommentsById(recipeId);
                recipe = new Recipe(recipeId, name, picture, description, instructions, authorId, tags, ingredients,
                        comments);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipe;
    }

    private Set<UUID> getRecipeCommentsById(UUID id) {
        Set<UUID> comments = new HashSet<>();
        try {
            useDatabase();
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getRecipeCommentsById);
            ps.setString(1, id.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                comments.add(UUID.fromString(rs.getString("id")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    private Map<Ingredient, Integer> getRecipeIngredientsById(UUID id) {
        Map<Ingredient, Integer> ingredients = new Hashtable<>();
        try {
            useDatabase();
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getRecipeIngredientsById);
            ps.setString(1, id.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ingredient ingredient = getIngredientById(UUID.fromString(rs.getString("ingredient_id")));
                Integer quantity = rs.getInt("quantity");

                ingredients.put(ingredient, quantity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    private Set<Tag> getRecipeTagsById(UUID id) {
        Set<Tag> tags = new HashSet<>();
        try {
            useDatabase();
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getRecipeTagsById);
            ps.setString(1, id.toString());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Tag tag = getTagById(UUID.fromString(rs.getString("tag_id")));
                tags.add(tag);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }

    public Recipe getRecipeByName(String name) {
        return null;
        // TODO: Implement
    }

    public List<Recipe> getRecipesByNameLike(String name) {
        List<Recipe> recipes = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getRecipesByNameLike);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Recipe recipe = getRecipeById(UUID.fromString(rs.getString("id")));
                recipes.add(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public String addRecipe(UUID id, String name, Image picture, String description, String instructions,
            UUID authorId) {
        try {
            PreparedStatement useDatabase = conn.prepareStatement(SqlQueries.useDatabase); // modified
            useDatabase.execute(); // modified
            PreparedStatement ps = conn.prepareStatement(SqlQueries.addRecipe);
            ps.setString(1, String.valueOf(id));
            ps.setString(2, name);
            ps.setString(3, String.valueOf(picture));
            ps.setString(4, description);
            ps.setString(5, instructions);
            ps.setString(6, String.valueOf(authorId));
            ps.executeUpdate();
            ps.close();
            useDatabase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return String.format(SuccessMessages.RECIPE_ADDED);
        // TODO: Implement
    }

    // ------------------- INGREDIENT -------------------//

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SqlQueries.getAllIngredients);

            while (rs.next()) {
                UUID ingredientId = UUID.fromString(rs.getString("id"));
                ingredients.add(getIngredientById(ingredientId));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    private Ingredient getIngredientById(UUID ingredientId) {
        Ingredient ingredient = null;
        try {
            useDatabase();
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getIngredientById);
            ps.setString(1, ingredientId.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("ingredient_name");
                String unit = rs.getString("unit");

                ingredient = new Ingredient(ingredientId, name, unit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredient;
    }

    public Ingredient getIngredientByName(String name) {
        return null;
        // TODO: Implement
    }

    // ------------------- MESSAGE -------------------//
    public String removeMessageById(UUID messageId) {
        try {
            PreparedStatement ps = conn.prepareStatement(SqlQueries.removeMessage);
            ps.setString(1, messageId.toString());

            int result = ps.executeUpdate();
            ps.close();

            if (result != 0) {
                return String.format(SuccessMessages.MESSAGE_DELETED);
            } else {
                return String.format(FailMessages.MESSAGE_DELETE_FAIL);
            }
        } catch (SQLException e) {
            return String.format(FailMessages.MESSAGE_DELETE_FAIL);
        }
    }

    // ------------------- TAG -------------------//
    public List<Tag> getAllTags() {
        List<Tag> tags = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SqlQueries.getAllTags);

            while (rs.next()) {
                UUID tagId = UUID.fromString(rs.getString("id"));
                tags.add(getTagById(tagId));
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }

    private Tag getTagById(UUID tagId) {
        Tag tag = null;
        try {
            useDatabase();
            PreparedStatement ps = conn.prepareStatement(SqlQueries.getTagById);
            ps.setString(1, tagId.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("tag_name");

                tag = new Tag(tagId, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tag;
    }
}