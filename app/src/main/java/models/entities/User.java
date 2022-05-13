package models.entities;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.Dictionary;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import util.common.Hasher;
import util.common.Validator;
import util.constants.FailMessages;
import util.constants.SuccessMessages;
import util.constants.Variables;
import util.exceptions.common.InvalidInstanceException;
import util.exceptions.common.InvalidLengthException;
import util.exceptions.user.*;

public class User extends BaseEntity {

    private String username;
    private String nickname;
    private String email;
    private String password;
    private Set<UUID> messages;
    private Dictionary<UUID, Date> weeklyList;
    private Set<UUID> favorites;
    private Dictionary<UUID, Integer> cart;
    private Set<UUID> recipes;

    // Creating a new user
    // public User(String username, String email, String password) throws
    // InvalidEmailException, TakenEmailException {
    // super();
    // setUsername(username);
    // setEmail(email);
    // setPassword(password);
    // }

    // Importing an existing user
    public User(UUID id, String username, String nickname, String email, String password,
            Dictionary<UUID, Integer> cart, Set<UUID> messages, Dictionary<UUID, Date> weeklyList,
            Set<UUID> favorites, Set<UUID> recipes)
            throws TakenUsernameException, InvalidUserNameLengthException, TakenNicknameException,
            InvalidNicknameLengthException, InvalidPasswordComplexityException, InvalidPasswordLengthException,
            InvalidEmailException, TakenEmailException {
        super.id = id;
        setUsername(username);
        setNickname(nickname);
        setEmail(email);
        setPassword(password);
        setCart(cart);
        setWeeklyList(weeklyList);
        setFavorites(favorites);
        setMessages(messages);
        setRecipes(recipes);
    }

    // OPERATIONS
    public String addRecipeToFavorites(UUID recipeId) {
        try {
            Validator.validateRecipe(recipeId);
            this.favorites.add(recipeId);
            return String.format(SuccessMessages.USER_ADDED_FAVORITE_RECIPE);
        } catch (InvalidInstanceException e) {
            return String.format(FailMessages.RECIPE_NOT_EXIST);
        }
    }

    public String removeRecipeFromFavorite(UUID recipeId) {
        if (favorites.remove(recipeId)) {
            return String.format(SuccessMessages.USER_REMOVED_FAVORITE_RECIPE);
        }
        return String.format(FailMessages.USER_RECIPE_NOT_FAVORITE);
    }

    // GETTERS
    public String getNickname() {
        return nickname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Set<UUID> getRecipes() {
        return recipes;
    }

    public Dictionary<UUID, Integer> getCart() {
        return cart;
    }

    public Set<UUID> getFavorites() {
        return favorites;
    }

    public Dictionary<UUID, Date> getWeeklyList() {
        return weeklyList;
    }

    public Set<UUID> getMessages() {
        return messages;
    }

    // SETTERS
    public String setPassword(String password)
            throws InvalidPasswordComplexityException, InvalidPasswordLengthException {
        // validatePassword(password);
        try {
            this.password = Hasher.hashString(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return String.format(SuccessMessages.USER_SET_PASSWORD);
    }

    public void setEmail(String email) throws InvalidEmailException, TakenEmailException {
        validateEmail(email);
        this.email = email;
    }

    public void setNickname(String nickname) throws TakenNicknameException, InvalidNicknameLengthException {
        validateNickname(nickname);
        this.nickname = nickname;
    }

    private String setUsername(String username) throws TakenUsernameException, InvalidUserNameLengthException {
        validateUsername(username);
        this.username = username;
        return String.format(SuccessMessages.USER_SET_USERNAME, username);

    }

    private void setWeeklyList(Dictionary<UUID, Date> weeklyList) {
        this.weeklyList = weeklyList;
    }

    private void setRecipes(Set<UUID> recipes) {
        this.recipes = recipes;
    }

    private void setMessages(Set<UUID> messages) {
        this.messages = messages;
    }

    private void setFavorites(Set<UUID> favorites) {
        this.favorites = favorites;
    }

    private void setCart(Dictionary<UUID, Integer> cart) {
        this.cart = cart;
    }

    // VALIDATORS
    private void validateLength(String string, int minLength, int maxLength) throws InvalidLengthException {
        Validator.validateStringLength(string, minLength, maxLength);
    }

    private void validateUsername(String username) throws TakenUsernameException, InvalidUserNameLengthException {
        try {
            validateLength(username, Variables.MIN_USER_NAME_LENGTH, Variables.MAX_USER_NAME_LENGTH);
        } catch (InvalidLengthException e) {
            throw new InvalidUserNameLengthException();
        }

        if (!isUniqueUsername(username)) {
            throw new TakenUsernameException(username);
        }
    }

    private void validateNickname(String nickname) throws TakenNicknameException, InvalidNicknameLengthException {
        try {
            validateLength(nickname, Variables.MIN_USER_NICK_LENGTH, Variables.MAX_USER_NICK_LENGTH);
        } catch (InvalidLengthException e) {
            throw new InvalidNicknameLengthException();
        }

        if (!isUniqueNickname(nickname)) {
            throw new TakenNicknameException(nickname);
        }
    }

    // private void validatePassword(String password)
    //         throws InvalidPasswordComplexityException {
    //     validatePasswordComplexity(password);
    // }

    private void validateEmail(String email) throws InvalidEmailException, TakenEmailException {
        if (!isEmail(email)) {
            throw new InvalidEmailException();
        }
        if (!isUniqueEmail(email)) {
            throw new TakenEmailException();
        }
    }

    private boolean isUniqueUsername(String username) {
        // if (username == "taken") { // TODO: DbContext.getUserByUsername(username);
        // see if username is taken
        // return false;
        // }
        return true;
    }

    private boolean isUniqueNickname(String nickname) {
        // if (nickname == "Taken") { // TODO: DbContext.getUserByNickname(nickname);
        // see if nickname is taken
        // return false;
        // }
        return true;
    }

    // private void validatePasswordComplexity(String password) throws InvalidPasswordComplexityException {
    //     boolean hasLowerCase = false;
    //     boolean hasUpperCase = false;
    //     boolean hasDigit = false;
    //     for (int i = 0; i < password.length(); i++) { // Check for lowercase, uppercase and digit
    //         Character currChar = password.charAt(i);
    //         if (Character.isLowerCase(currChar)
    //                 || !hasLowerCase) {
    //             hasLowerCase = true;
    //         } else if (Character.isUpperCase(currChar)
    //                 || !hasUpperCase) {
    //             hasUpperCase = true;
    //         } else if (Character.isDigit(currChar)
    //                 || !hasDigit) {
    //             hasDigit = true;
    //         }
    //         if (hasLowerCase && hasUpperCase && hasDigit) {
    //             break;
    //         }

    //         if (!hasLowerCase
    //                 || !hasUpperCase
    //                 || !hasDigit) {
    //             throw new InvalidPasswordComplexityException();
    //         }
    //     }
    // }

    private boolean isEmail(String email) {
        Pattern pattern = Pattern.compile(Variables.USER_EMAIL_REGEX,
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return (matcher.find() || email == null);
    }

    private boolean isUniqueEmail(String email) {
        return true;
    }

}