package models.entities;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Dictionary;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.common.Validator;
import util.constants.FailMessages;
import util.constants.SuccessMessages;
import util.constants.Variables;
import util.exceptions.common.InvalidInstanceException;
import util.exceptions.common.InvalidLengthException;
import util.exceptions.user.InvalidEmailException;
import util.exceptions.user.InvalidPasswordComplexityException;
import util.exceptions.user.TakenEmailException;
import util.exceptions.user.TakenNicknameException;
import util.exceptions.user.TakenUsernameException;

public class User extends BaseEntity {

    private String username;
    private String nickname;
    private String email;
    private String password;
    private Set<Message> messages;
    private Set<Recipe> weeklyList;
    private Set<Recipe> favorites;
    private Dictionary<Ingredient, Integer> cart;
    private Set<Recipe> recipes;

    // Creating a new user
    public User(String username, String nickname, String email, String password) {
        super();
        setUsername(username);
        setNickname(nickname);
        setEmail(email);
        setPassword(password);
    }

    // Importing an existing user
    public User(UUID id, String username, String nickname, String email, String password,
            Dictionary<Ingredient, Integer> cart, Set<Message> messages, Set<Recipe> weeklyList,
            Set<Recipe> favorites, Set<Recipe> recipes) {
        super.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        setCart(cart);
        setWeeklyList(weeklyList);
        setFavorites(favorites);
        setMessages(messages);
        setRecipes(recipes);
    }

    
    // OPERATIONS
    public String addRecipeToFavorites(Recipe recipe) {
        try {
            Validator.validateExists(recipe);
            this.favorites.add(recipe);
            return String.format(SuccessMessages.USER_ADDED_FAVORITE_RECIPE);
        } catch (InvalidInstanceException e) {
            return String.format(FailMessages.RECIPE_NOT_EXIST);
        }
    }

    public String removeRecipeFromFavorite(Recipe recipe) {
        if (favorites.remove(recipe))
        {
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

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public Dictionary<Ingredient, Integer> getCart() {
        return cart;
    }

    public Set<Recipe> getFavorites() {
        return favorites;
    }

    public Set<Recipe> getWeeklyList() {
        return weeklyList;
    }

    public Set<Message> getMessages() {
        return messages;
    }



    // SETTERS
    public String setPassword(String password) {
        try {
            validatePassword(password);
            this.password = toHexString(getSHA(password));
            return String.format(SuccessMessages.USER_SET_PASSWORD);
        } catch (InvalidLengthException e) {
            return String.format(FailMessages.USER_INVALID_PASSWORD_LENGTH);
        } catch (NoSuchAlgorithmException e) {
            return String.format(FailMessages.INVALID_ALGORITHM);
        } catch (InvalidPasswordComplexityException e) {
            return String.format(FailMessages.USER_INVALID_PASSWORD_COMPLEXITY);
        }
    }

    public String setEmail(String email) {
        try {
            validateEmail(email);
            this.email = email;
            // TODO: DbContext.updateUserEmail(UUID id, String email);
            return String.format(SuccessMessages.USER_SET_EMAIL, email);
        } catch (InvalidEmailException e) {
            return String.format(FailMessages.USER_INVALID_EMAIL);
        } catch (TakenEmailException e) {
            return String.format(FailMessages.USER_EMAIL_TAKEN);
        }
    }

    public String setNickname(String nickname) {
        try {
            validateNickname(nickname);
            this.nickname = nickname;
            // TODO: DbContext.updateUserNickname(UUID id, String nickname);
            return String.format(SuccessMessages.USER_SET_NICKNAME, nickname);
        } catch (InvalidLengthException e) {
            return String.format(FailMessages.USER_INVALID_NICKNAME_LENGTH);
        } catch (TakenNicknameException e) {
            return String.format(FailMessages.USER_NICK_TAKEN, nickname);
        }
    }
    
    private String setUsername(String username) {
        try {
            validateUsername(username);
            this.username = username;
            // TODO: DbContext.updateUserName(UUID id, String username);
            return String.format(SuccessMessages.USER_SET_USERNAME, username);
        } catch (TakenUsernameException e) {
            return String.format(FailMessages.USER_NAME_TAKEN, username);
        } catch (InvalidLengthException e) {
            return String.format(FailMessages.USER_INVALID_NAME_LENGTH);
        }
    }

    private void setWeeklyList(Set<Recipe> weeklyList) {
        weeklyList = null; // TODO: DbContext.getUserList(String Id); SQL Query
    }

    private void setRecipes(Set<Recipe> recipes) {
        recipes = null; // TODO: DbContext.getUserRecipes(String id);
    }

    private void setMessages(Set<Message> messages) {
        messages = null; // TODO: DbContext.getUserMessages(String Id); SQL Query
    }

    private void setFavorites(Set<Recipe> favorites) {
        favorites = null; // TODO: DbContext.getUserFavorites(String Id); SQL Query
    }

    private void setCart(Dictionary<Ingredient, Integer> cart) {
        cart = null; // TODO: DbContext.getUserCart(String Id); SQL Query
    }



    // VALIDATORS
    private void validateLength(String nickname, int minLength, int maxLength) throws InvalidLengthException {
        Validator.validateStringLength(nickname, minLength, maxLength);
    }

    private void validateUsername(String username)
            throws InvalidLengthException, TakenUsernameException {
        validateLength(username, Variables.MIN_USER_NAME_LENGTH, Variables.MAX_USER_NAME_LENGTH);

        if (isUniqueUsername(username)) {
            throw new TakenUsernameException(username);
        }
    }

    private void validateNickname(String nickname)
            throws InvalidLengthException, TakenNicknameException {
        validateLength(nickname, Variables.MIN_USER_NICK_LENGTH, Variables.MAX_USER_NICK_LENGTH);

        if (!isUniqueNickname(nickname)) {
            throw new TakenNicknameException(nickname);
        }
    }

    private void validatePassword(String password) throws InvalidLengthException, InvalidPasswordComplexityException {
        validateLength(password, Variables.MIN_PASSWORD_LENGTH, Variables.MAX_PASSWORD_LENGTH);
        validatePasswordComplexity(password);
    }

    private void validateEmail(String email) throws InvalidEmailException, TakenEmailException {
        if (!isEmail(email)) {
            throw new InvalidEmailException();
        }
        if (!isUniqueEmail(email)) {
            throw new TakenEmailException();
        }
    }

    private boolean isUniqueUsername(String username) {
        if (username == "taken") { // TODO: DbContext.getUserByUsername(username); see if username is taken
            return false;
        }
        return true;
    }

    private boolean isUniqueNickname(String nickname) {
        if (nickname == "Taken") { // TODO: DbContext.getUserByNickname(nickname); see if nickname is taken
            return false;
        }
        return true;
    }

    private void validatePasswordComplexity(String password) throws InvalidPasswordComplexityException {
        boolean hasLowerCase = false;
        boolean hasUpperCase = false;
        boolean hasDigit = false;
        for (int i = 0; i < password.length(); i++) { // Check for lowercase, uppercase and digit
            Character currChar = password.charAt(i);
            if (Character.isLowerCase(currChar)
                    || !hasLowerCase) {
                hasLowerCase = true;
            } else if (Character.isUpperCase(currChar)
                    || !hasUpperCase) {
                hasUpperCase = true;
            } else if (Character.isDigit(currChar)
                    || !hasDigit) {
                hasDigit = true;
            }
            if (hasLowerCase && hasUpperCase && hasDigit) {
                break;
            }

            if (!hasLowerCase
                    || !hasUpperCase
                    || !hasDigit) {
                throw new InvalidPasswordComplexityException();
            }
        }
    }

    private boolean isEmail(String email) {
        Pattern pattern = Pattern.compile(Variables.USER_EMAIL_REGEX,
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return (matcher.find() || email == null);
    }

    private boolean isUniqueEmail(String email) {
        // TODO: DbContext.getUserWithEmail(String email);
        return true;
    }

    private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

}