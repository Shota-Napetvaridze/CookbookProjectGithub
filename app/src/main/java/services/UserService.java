package services;

import java.util.Date;
import java.util.Set;

import model.Comment;
import model.Ingredient;
import model.Recipe;
import model.User;

public interface UserService {
    public Set<User> getUsers();
    public User addUser(String username, String nickname, String email, String password);
    public String removeUser(User user);

    public String changeNickname(User user, String nickname);
    public String changeEmail(User user, String email);
    public String adminChangeNickname(User user, User target, String nickname);
    public String adminChangeEmail(User user, User target, String email);

    public String sendMessage(User user, String message);
    public String sendRecipe(User user, Recipe recipe, String message);

    public String addToCart(Ingredient ingredient, int amount);
    public String removeFromCart(Ingredient ingredient);
    public String changeAmount(Ingredient ingredient, int amount);
    public String generateCart();

    public String addToFavorites(Recipe recipe);
    public String removeFromFavorites(Recipe recipe);

    public String addToList(Recipe recipe, Date date);
    public String removeFromList(Recipe recipe, Date date);

    public String addComment(Comment comment);
    public String editComment(Comment oldComment, Comment newComment);
    public String removeComment(Comment comment);
}
