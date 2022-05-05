package util.constants;

public class SqlQueries {
        public static final String dropDatabase = "DROP DATABASE ?";
        public static final String createDatabase = "CREATE DATABASE ?";
        public static final String useDatabase = "USE ?";

        public static final String createTableTags = "CREATE TABLE tags ("
                        + "id CHAR(38) NOT NULL PRIMARY KEY,"
                        + "tag_name VARCHAR(20) NOT NULL"
                        + ");";

        public static final String createTableUsers = "CREATE TABLE users ("
                        + "id CHAR(38) NOT NULL PRIMARY KEY,"
                        + "username VARCHAR(30) NOT NULL,"
                        + "display_name VARCHAR(30),"
                        + "email VARCHAR(50) NOT NULL,"
                        + "password CHAR(64) NOT NULL"
                        + ");";

        public static final String createTableMessages = "CRECREATE TABLE messages ("
                        + "id CHAR(38) NOT NULL PRIMARY KEY,"
                        + "sender_id CHAR(38) NOT NULL"
                        + "REFERENCES users(id),"
                        + "receiver_id CHAR(38) NOT NULL"
                        + "REFERENCES users(id),"
                        + "message_text VARCHAR(300) NOT NULL,"
                        + "is_read BOOL NOT NULL"
                        + ");";

        public static final String createTableIngredients = "CREATE TABLE ingredients ("
                        + "id CHAR(38) NOT NULL PRIMARY KEY,"
                        + "ingredient_name VARCHAR(20) NOT NULL,"
                        + "unit VARCHAR(20) NOT NULL"
                        + ");";

        public static final String createTableUserIngredients = "CREATE TABLE users_ingredients ("
                        + "user_id CHAR(38) NOT NULL"
                        + "REFERENCES users(id),"
                        + "ingredient_id CHAR(38) NOT NULL"
                        + "REFERENCES ingredients(id),"
                        + "quantity FLOAT NOT NULL,"
                        + "PRIMARY KEY (user_id, ingredient_id)"
                        + ");";

        public static final String createTableRecipes = "CREATE TABLE recipes ("
                        + "id CHAR(38) NOT NULL PRIMARY KEY,"
                        + "name VARCHAR(30) NOT NULL,"
                        + "picture BLOB NOT NULL,"
                        + "description VARCHAR(1500) NOT NULL,"
                        + "instructions VARCHAR(1500) NOT NULL,"
                        + "serving_size BIT NOT NULL,"
                        + "author_id CHAR(38) NOT NULL"
                        + "REFERENCES users(id)"
                        + ");";

        public static final String createTableComments = "CREATE TABLE comments ("
                        + "id CHAR(38) NOT NULL PRIMARY KEY,"
                        + "user_id CHAR(38) NOT NULL"
                        + "REFERENCES users(id),"
                        + "recipe_id CHAR(38) NOT NULL"
                        + "REFERENCES recipes(id),"
                        + "text VARCHAR(300) NOT NULL"
                        + ");";

        public static final String createTableRecipeIngredients = "CREATE TABLE recipes_ingredients ("
                        + "recipe_id CHAR(38) NOT NULL"
                        + "REFERENCES recipes(id),"
                        + "ingredient_id CHAR(38) NOT NULL"
                        + "REFERENCES ingredients(id),"
                        + "quantity FLOAT NOT NULL,"
                        + "PRIMARY KEY (recipe_id, ingredient_id)"
                        + ");";

        public static final String createTableRecipeTags = "CREATE TABLE recipes_tags ("
                        + "recipe_id CHAR(38) NOT NULL"
                        + "REFERENCES recipes(id),"
                        + "tag_id CHAR(38) NOT NULL"
                        + "REFERENCES tags(id),"
                        + "PRIMARY KEY(recipe_id, tag_id)"
                        + ");";

        public static final String createTableUsersFavorites = "CREATE TABLE users_favorites ("
                        + "user_id CHAR(38) NOT NULL"
                        + "REFERENCES users(id),"
                        + "recipe_id CHAR(38) NOT NULL"
                        + "REFERENCES recipes(id),"
                        + "PRIMARY KEY (user_id, recipe_id)"
                        + ");";

        public static final String createTableWeeklyPlans = "CREATE TABLE weekly_plans ("
                        + "user_id CHAR(38) NOT NULL"
                        + "REFERENCES users(id),"
                        + "recipe_id CHAR(38)"
                        + "REFERENCES recipes(id),"
                        + "Day DATE NOT NULL,"
                        + "PRIMARY KEY (user_id, recipe_id)"
                        + ");";

        public static final String createTableUsersRecipeTags = "CREATE TABLE users_recipes_tags ("
                        + "user_id CHAR(38) NOT NULL"
                        + "REFERENCES users(id),"
                        + "recipe_id CHAR(38) NOT NULL"
                        + "REFERENCES recipes(id),"
                        + "tag_id CHAR(38) NOT NULL"
                        + "REFERENCES tags(id),"
                        + "PRIMARY KEY (user_id, recipe_id, tag_id)"
                        + ");";

        public static final String addRecipe = "INSERT INTO Recipes (\n" +
                        "Id, RecipeName, Picture, RecipeDescription, Instructions, AuthorId) \n" +
                        "VALUES (?, ?, ?, ?, ?)";

        public static final String addUser = "INSERT INTO Users (\n" +
                        "Id, user_name, DisplayName, Email, Password) \n" +
                        "VALUES (?, ?, ?, ?, ?)";
}
