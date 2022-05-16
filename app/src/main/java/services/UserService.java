package services;

import java.sql.Date;
import java.util.Dictionary;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import models.entities.Message;
import models.entities.Recipe;
import models.entities.User;
import util.exceptions.common.InvalidLengthException;
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
    
    public Set<User> getUsers();
    public User getUserById(UUID id);

    public User getUserByNickname(String nickname);
    public String addUser(String username, String email, String password) throws InvalidLengthException, TakenNicknameException, InvalidEmailException, TakenEmailException, TakenUsernameException, InvalidUserNameLengthException, InvalidNicknameLengthException, InvalidPasswordComplexityException, InvalidPasswordLengthException;
    public String removeUserById(UUID userId);

    public String changeNickname(UUID userId, String nickname) throws TakenNicknameException, InvalidNicknameLengthException;
    public String changeEmail(UUID userId, String email);

    public List<Recipe> getFavoriteRecipes(UUID userId);
    
    public List<Message> getUserMessagesById(UUID userId);
    public String sendMessage(UUID senderId, UUID receiverId, String message);
    public String sendRecipe(UUID senderId, UUID receiverId, UUID recipeId, String message);
    public String removeMessageById(UUID messageId);

    public String addToCart(UUID ingredientId, int amount);
    public String removeFromCart(UUID ingredientId);
    public String changeAmount(UUID ingredientId, int amount);

    public boolean addToFavorites(UUID userId, UUID recipeId);
    public boolean removeFromFavorites(UUID userId, UUID recipeId);

    public Dictionary<UUID, Date> getWeeklyPlan(UUID userId);
    public String addToPlan(UUID recipeId, Date date);
    public String removeFromPlan(UUID recipeId, Date date);

    public String addComment(UUID commentId);
    public String editComment(UUID oldCommentId, UUID newCommentId);
    public String removeComment(UUID commentId);
}
