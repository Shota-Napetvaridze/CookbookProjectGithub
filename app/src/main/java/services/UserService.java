package services;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import models.entities.Ingredient;
import models.entities.Message;
import models.entities.Recipe;
import models.entities.User;
import util.exceptions.user.InvalidEmailException;
import util.exceptions.user.InvalidNicknameLengthException;
import util.exceptions.user.InvalidPasswordComplexityException;
import util.exceptions.user.InvalidPasswordLengthException;
import util.exceptions.user.InvalidUserNameLengthException;
import util.exceptions.user.TakenEmailException;
import util.exceptions.user.TakenNicknameException;
import util.exceptions.user.TakenUsernameException;

public interface UserService {
    public User loginUser(String username, String password);

    public List<User> getUsers();

    public List<User> getUsersLike(String text);

    public User getUserById(UUID id);

    public User getUserByNickname(String nickname);

    public String addUser(UUID userId, String username, String email, String password)
            throws TakenUsernameException, InvalidUserNameLengthException, TakenEmailException, InvalidEmailException,
            InvalidPasswordLengthException, InvalidPasswordComplexityException, NoSuchAlgorithmException;

    public String removeUserById(UUID userId);

    public String changeUsername(UUID id, String username)
            throws TakenUsernameException, InvalidUserNameLengthException;

    public String changeNickname(UUID userId, String nickname)
            throws InvalidNicknameLengthException, TakenNicknameException;

    public String changeEmail(UUID userId, String email) throws TakenEmailException, InvalidEmailException;

    public String changePassword(UUID userId, String password)
            throws InvalidPasswordLengthException, InvalidPasswordComplexityException, NoSuchAlgorithmException;

    public List<Recipe> getFavoriteRecipes(UUID userId);

    public List<Message> getUserMessagesById(UUID userId);

    public String sendMessage(UUID messageId, UUID senderId, UUID receiverId, String message, UUID recipeId);

    public String removeMessageById(UUID messageId);

    public Map<Ingredient, Integer> getUserCartById(UUID id);

    public String addToCart(UUID ingredientId, int amount);

    public String removeFromCart(UUID ingredientId);

    public String changeAmount(UUID ingredientId, int amount);

    public boolean addToFavorites(UUID userId, UUID recipeId);

    public boolean removeFromFavorites(UUID userId, UUID recipeId);

    public Map<Recipe, Date> getWeeklyPlan(UUID userId);

    public String addToPlan(UUID userId, UUID recipeId, Date date);

    public String removeFromPlan(UUID userId, UUID recipeId);

    public String addComment(UUID commentId);

    public String editComment(UUID oldCommentId, UUID newCommentId);

    public String removeComment(UUID commentId);

    public void validateUsername(String username) throws TakenUsernameException, InvalidUserNameLengthException;

    public void validateNickname(String nickname) throws InvalidNicknameLengthException, TakenNicknameException;

    public void validateEmail(String email) throws TakenEmailException, InvalidEmailException;

    public void validatePassword(String password)
            throws InvalidPasswordLengthException, InvalidPasswordComplexityException;
}
