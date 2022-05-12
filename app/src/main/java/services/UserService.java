package services;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import models.entities.Message;
import models.entities.User;

public interface UserService {
    public User loginUser(String username, String password);
    
    public Set<User> getUsers();
    public User getUserById(UUID id);
    public User getUserByNickname(String nickname);
    public String addUser(String username, String email, String password);
    public String removeUserById(UUID userId);

    public String changeNickname(UUID userId, String nickname);
    public String changeEmail(UUID userId, String email);

    public List<Message> getUserMessagesById(UUID userId);
    public String sendMessage(UUID senderId, UUID receiverId, String message);
    public String sendRecipe(UUID senderId, UUID receiverId, UUID recipeId, String message);
    public String removeMessageById(UUID messageId);

    public String addToCart(UUID ingredientId, int amount);
    public String removeFromCart(UUID ingredientId);
    public String changeAmount(UUID ingredientId, int amount);

    public boolean addToFavorites(UUID userId, UUID recipeId);
    public boolean removeFromFavorites(UUID userId, UUID recipeId);


    public String addToList(UUID recipeId, Date date);
    public String removeFromList(UUID recipeId, Date date);

    public String addComment(UUID commentId);
    public String editComment(UUID oldCommentId, UUID newCommentId);
    public String removeComment(UUID commentId);
}
