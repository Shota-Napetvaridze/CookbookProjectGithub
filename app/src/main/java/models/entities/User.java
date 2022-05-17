package models.entities;

import java.sql.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import util.constants.FailMessages;
import util.constants.SuccessMessages;

public class User extends BaseEntity {

    private String username;
    private String nickname;
    private String email;
    private String password;
    private Set<UUID> messages;
    private Map<UUID, Date> weeklyList;
    private Set<UUID> favorites;
    private Map<UUID, Integer> cart;
    private Set<UUID> recipes;

    public User(UUID id, String username, String nickname, String email, String password,
            Map<UUID, Integer> cart, Set<UUID> messages, Map<UUID, Date> weeklyList,
            Set<UUID> favorites, Set<UUID> recipes) {
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
            this.favorites.add(recipeId);
            return String.format(SuccessMessages.USER_ADDED_FAVORITE_RECIPE);
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

    public Map<UUID, Integer> getCart() {
        return cart;
    }

    public Set<UUID> getFavorites() {
        return favorites;
    }

    public Map<UUID, Date> getWeeklyList() {
        return weeklyList;
    }

    public Set<UUID> getMessages() {
        return messages;
    }

    // SETTERS
    public String setPassword(String password) {
        this.password = password;
        return String.format(SuccessMessages.USER_SET_PASSWORD);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String setUsername(String username) {
        this.username = username;
        return String.format(SuccessMessages.USER_SET_USERNAME, username);

    }

    private void setWeeklyList(Map<UUID, Date> weeklyList) {
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

    private void setCart(Map<UUID, Integer> cart) {
        this.cart = cart;
    }
}