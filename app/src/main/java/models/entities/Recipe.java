package models.entities;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import util.constants.SuccessMessages;
import javafx.scene.image.Image;

public class Recipe extends BaseEntity {

    private String name;
    private Image picture;
    private String description;
    private String instructions;
    private User author;
    private List<Tag> tags;
    private Map<Ingredient, Integer> ingredients;
    private byte servingSize;
    private List<Comment> comments;


    public Recipe(UUID id, String name, Image picture, String description,
    String instructions, User author, List<Tag> tags,
    Map<Ingredient, Integer> ingredients, List<Comment> comments, byte servingSize) {
        super.id = id;
        setName(name);
        setPicture(picture);
        setDescription(description);
        setInstructions(instructions);
        setAuthor(author);
        setTags(tags);
        setIngredients(ingredients);
        setComments(comments);
        setServingSize(servingSize);
    }

    // GETTERS
    public String getName() {
        return name;
    }

    public Image getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }

    public String getInstructions() {
        return instructions;
    }

    public User getAuthor() {
        return author;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Map<Ingredient, Integer> getIngredients() {
        return ingredients;
    }

    public byte getServingSize() {
        return servingSize;
    }

    public List<Comment> getComments() {
        return comments;
    }
    
    

    // SETTERS
    public String setName(String name) {
            this.name = name;
            return String.format(SuccessMessages.RECIPE_SET_NAME, name);
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }

    public String setDescription(String description) {
            this.description = description;
            return String.format(SuccessMessages.RECIPE_SET_DESC);
    }

    public String setInstructions(String instructions) {
            this.instructions = instructions;
            return String.format(SuccessMessages.RECIPE_SET_INSTRUCTIONS);
    }

    public String setTags(List<Tag> tags) {
            this.tags = tags;
            return String.format(SuccessMessages.RECIPE_SET_TAGS);
    }

    public String setIngredients(Map<Ingredient, Integer> ingredients) {
            this.ingredients = ingredients;
            return String.format(SuccessMessages.RECIPE_SET_INGREDIENTS);
    }

    public String setServingSize(byte servingSize) {
            this.servingSize = servingSize;
            return String.format(SuccessMessages.RECIPE_SET_SERVING_SIZE, servingSize);
    }

    private String setAuthor(User author) {
            this.author = author;
            return String.format(SuccessMessages.RECIPE_SET_AUTHOR);
    }
    
    private void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
