package services.impl;

import java.sql.Date;
import java.util.Dictionary;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import models.entities.Message;
import models.entities.Recipe;
import models.entities.User;
import services.UserService;
import util.common.DbContext;
import util.constants.FailMessages;
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

public class UserServiceImpl implements UserService {
    private DbContext dbContext;

    public UserServiceImpl() {
        super();
        dbContext = new DbContext(Variables.DATABASE_PORT, Variables.DATABASE_USER, Variables.DATABASE_PASS);
    }

    @Override
    public Set<User> getUsers() {
        return dbContext.getAllUsers();
    }

    @Override
    public String addUser(String username, String email, String password)
            throws TakenUsernameException, InvalidUserNameLengthException, TakenNicknameException,
            InvalidNicknameLengthException, InvalidPasswordComplexityException, InvalidPasswordLengthException,
            InvalidEmailException, TakenEmailException {
        boolean isUnique = dbContext.validateUniqueCredentials(username, email);
        if (isUnique) {
            return dbContext.addUser(username, email, password);
        }
        return String.format(FailMessages.USER_ADD_FAIL);
    }

    @Override
    public String removeUserById(UUID userId) {
        return dbContext.removeUserById(userId);
    }

    @Override
    public String changeNickname(UUID userId, String nickname)
            throws TakenNicknameException, InvalidNicknameLengthException {
        User user = dbContext.getUserById(userId);

        user.setNickname(nickname);
        return dbContext.userChangeNickname(userId, nickname);
    }

    @Override
    public String changeEmail(UUID userId, String email) {
        User user = dbContext.getUserById(userId);

        try {
            user.setEmail(email);
            return dbContext.userChangeEmail(userId, email);
        } catch (InvalidEmailException e) {
            return String.format(FailMessages.USER_INVALID_EMAIL);
        } catch (TakenEmailException e) {
            return String.format(FailMessages.USER_EMAIL_TAKEN);
        }
    }

    @Override
    public String sendMessage(UUID senderId, UUID receiverId, String message) {
        dbContext.sendMessage(senderId, receiverId, message);
        return null;
    }

    @Override
    public String sendRecipe(UUID senderId, UUID receiverId, UUID recipeId, String message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String addToCart(UUID ingredientId, int amount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String removeFromCart(UUID ingredientId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String changeAmount(UUID ingredientId, int amount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean addToFavorites(UUID userId, UUID recipeId) {
        return dbContext.addRecipeToFavorites(userId, recipeId);
    }

    @Override
    public boolean removeFromFavorites(UUID userId, UUID recipeId) {
        return dbContext.removeRecipeFromFavorites(userId, recipeId);
    }

    @Override
    public String addToPlan(UUID recipeId, Date date) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String removeFromPlan(UUID recipeId, Date date) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String addComment(UUID commentId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String editComment(UUID oldCommentId, UUID newCommentId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String removeComment(UUID commentId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User loginUser(String username, String password) {
        return dbContext.getUserByCredentials(username, password);
    }

    @Override
    public User getUserById(UUID id) {
        return dbContext.getUserById(id);
    }

    @Override
    public String removeMessageById(UUID messageId) {
        return dbContext.removeMessageById(messageId);
    }

    @Override
    public List<Message> getUserMessagesById(UUID userId) {
        return dbContext.getUserMessages(userId);
    }

    @Override
    public User getUserByNickname(String nickname) {
        return dbContext.getUserByNickname(nickname);
    }

    @Override
    public List<Recipe> getFavoriteRecipes(UUID userId) {
        return dbContext.getFavoriteRecipes(userId);
    }

    public Dictionary<UUID, Date> getWeeklyPlan(UUID userId) {
        return dbContext.getUserWeeklyList(userId);
    }

    public List<Recipe> getPlanRecipes(UUID id) {
        return null;
    }

}
