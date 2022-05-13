package util.constants;

public class FailMessages {
        //
        // USER
        //
        public static final String USER_NOT_EXIST = "User does not exist.";
        public static final String USER_INVALID_NAME_LENGTH = "Username must be between "
                        + Variables.MIN_USER_NAME_LENGTH + " and " + Variables.MAX_USER_NAME_LENGTH
                        + " characters long.";

        public static final String USER_NAME_TAKEN = "Username %s is already taken.";

        public static final String USER_INVALID_PASSWORD_LENGTH = "Password must be between "
                        + Variables.MIN_PASSWORD_LENGTH + " and " + Variables.MAX_PASSWORD_LENGTH
                        + " characters long.";

        public static final String USER_INVALID_PASSWORD_COMPLEXITY = "Password must include "
                        + "at least one uppercase letter, one lowercase letter and one digit.";

        public static final String USER_INVALID_EMAIL = "Please enter a valid email.";

        public static final String USER_EMAIL_TAKEN = "Email is already taken.";

        public static final String USER_INVALID_NICKNAME_LENGTH = "Nickname must be between "
                        + Variables.MIN_USER_NICK_LENGTH + " and "
                        + Variables.MAX_USER_NICK_LENGTH + " characters long.";
        public static final String USER_NICK_TAKEN = "Nickname %s is already taken.";
        
        public static final String USER_RECIPE_NOT_FAVORITE = "Recipe is not in favorites.";

        public static final String USER_ADD_FAIL = "Could not add user.";
        public static final String USER_DELETE_FAIL = "Could not delete user.";
        public static final String USER_SET_NICKNAME_FAIL = "Could not change user nickname.";
        public static final String USER_SET_EMAIL_FAIL = "Could not change user email.";
        public static final String USER_ADD_FAVORITE_RECIPE_FAIL = "Could not add recipe to favorites.";
        //
        // TAG
        //
        public static final String TAG_INVALID_NAME_LENGTH = "Tag name must be between "
                        + Variables.MIN_TAG_NAME_LENGTH + " and " + Variables.MAX_TAG_NAME_LENGTH
                        + " characters long.";

        //
        // RECIPE
        //
        public static final String RECIPE_INVALID_NAME_LENGTH = "Recipe name must be between "
                        + Variables.MIN_RECIPE_NAME_LENGTH + " and " +
                        Variables.MAX_RECIPE_NAME_LENGTH + " characters long.";

        public static final String RECIPE_INVALID_DESCRIPTION_LENGTH = "Recipe description must be "
                        + "between " + Variables.MIN_RECIPE_DESC_LENGTH + " and "
                        + Variables.MAX_RECIPE_DESC_LENGTH + " characters long.";

        public static final String RECIPE_INVALID_INSTRUCTIONS_LENGTH = "Recipe instructions must be "
                        + "between " + Variables.MIN_RECIPE_INSTRUCTIONS_LENGTH + " and "
                        + Variables.MAX_RECIPE_INSTRUCTIONS_LENGTH + " characters long.";

        public static final String RECIPE_INVALID_INGREDIENTS_COUNT = "Recipe must have between "
                        + Variables.MIN_RECIPE_INGREDIENTS + " and "
                        + Variables.MAX_RECIPE_INGREDIENTS + " ingredients.";

        public static final String RECIPE_INVALID_TAGS_COUNT = "Recipe must have between "
                        + Variables.MIN_RECIPE_TAGS + " and "
                        + Variables.MAX_RECIPE_TAGS + " tags.";

        public static final String RECIPE_INVALID_SERVING_SIZE = "Recipe serving size must be "
                        + "even number between " + Variables.MIN_RECIPE_SERVING_SIZE + " and "
                        + Variables.MAX_RECIPE_SERVING_SIZE + ".";

        public static final String RECIPE_NOT_EXIST = "Recipe does not exist.";

        //
        // MESSAGE
        //
        public static final String MESSAGE_INVALID_TEXT_LENGTH = "Message must be between "
                        + Variables.MIN_MESSAGE_TEXT_LENGTH + " and "
                        + Variables.MAX_MESSAGE_TEXT_LENGTH + " characters long.";

        public static final String MESSAGE_INVALID_RECEIVER = "Could not send message.";

        public static final String MESSAGE_INVALID_SENDER = "Could not send message.";
        public static final String MESSAGE_DELETE_FAIL = "Could not delete message.";

        //
        // INGREDIENT
        //
        public static final String INGREDIENT_INVALID_NAME_LENGTH = "Ingredient name must be "
                        + "between " + Variables.MIN_INGREDIENT_NAME_LENGTH + " and "
                        + Variables.MAX_INGREDIENT_NAME_LENGTH + " characters long.";

        public static final String INGREDIENT_NOT_EXIST = "Ingredient %s does not exist.";
        public static final String INGREDIENT_UNIT_NOT_EXIST = "Unit %s does not exist.";
        public static final String INGREDIENT_NAME_TAKEN = "Ingredient %s already exists.";

        //
        // COMMENT
        //
        public static final String COMMENT_INVALID_TEXT_LENGTH = "Comment should be between "
                        + Variables.MIN_COMMENT_TEXT_LENGTH + " and "
                        + Variables.MAX_COMMENT_TEXT_LENGTH + " characters long.";
        //
        // MISC
        //
        public static final String INVALID_ALGORITHM = "There was an error. "
                        + "Could not save password.";

        public static final String INVALID_LENGTH = "Invalid length.";
        public static final String INVALID_COUNT = "Invalid count.";
        public static final String INVALID_INSTANCE = "Invalid instance.";

}
