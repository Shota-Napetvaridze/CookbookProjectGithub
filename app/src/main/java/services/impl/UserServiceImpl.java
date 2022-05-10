package services.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;


import models.entities.Message;
import models.entities.User;
import services.UserService;
import util.common.DbContext;
import util.constants.FailMessages;
import util.constants.Variables;
import util.exceptions.common.InvalidLengthException;
import util.exceptions.user.InvalidEmailException;
import util.exceptions.user.TakenEmailException;
import util.exceptions.user.TakenNicknameException;


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
    public String addUser(String username, String email, String password) {
        // User user = new User(username, email, password);
        return dbContext.addUser(username, email, password);
    }

    @Override
    public String removeUserById(UUID userId) {
        return dbContext.removeUserById(userId);
    }

    @Override
    public String changeNickname(UUID userId, String nickname) {
        User user = dbContext.getUserById(userId);

        try {
            user.setNickname(nickname);
            return dbContext.userChangeNickname(userId, nickname);
        } catch (InvalidLengthException e) {
            return String.format(FailMessages.USER_INVALID_NICKNAME_LENGTH);
        } catch (TakenNicknameException e) {
            return String.format(FailMessages.USER_NICK_TAKEN, nickname);
        }
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
    public String removeFromFavorites(UUID recipeId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String addToList(UUID recipeId, Date date) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String removeFromList(UUID recipeId, Date date) {
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

}
