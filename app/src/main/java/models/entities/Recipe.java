package models.entities;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import util.common.Validator;
import util.constants.FailMessages;
import util.constants.SuccessMessages;
import util.constants.Variables;
import util.exceptions.common.InvalidCountException;
import util.exceptions.common.InvalidInstanceException;
import util.exceptions.common.InvalidLengthException;
import javafx.scene.image.Image;

public class Recipe extends BaseEntity {

    private String name;
    private Image picture;
    private String description;
    private String instructions;
    private UUID authorId;
    private Set<Tag> tags = new HashSet<>();
    private Map<Ingredient, Integer> ingredients = new Hashtable<>();
    private byte servingSize;
    private Set<UUID> commentsIds;

    public Recipe(){}

    public Recipe(UUID id, String name, Image picture, String description,
            String instructions, UUID authorId, Set<Tag> tags,
            Map<Ingredient, Integer> ingredients, Set<UUID> comments) {
        super.id = id;
        setName(name);
        setPicture(picture);
        setDescription(description);
        setInstructions(instructions);
        setAuthor(authorId);
        setTags(tags);
        setIngredients(ingredients);
        setComments(comments);
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

    public UUID getAuthor() {
        return authorId;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Map<Ingredient, Integer> getIngredients() {
        return ingredients;
    }

    public byte getServingSize() {
        return servingSize;
    }

    public Set<UUID> getComments() {
        return commentsIds;
    }
    
    

    // SETTERS
    public String setName(String name) {
        try {
            validateName(name);
            this.name = name;
            return String.format(SuccessMessages.RECIPE_SET_NAME, name);
        } catch (InvalidLengthException e) {
            return String.format(FailMessages.RECIPE_INVALID_NAME_LENGTH);
        }
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }

    public String setDescription(String description) {
        try {
            validateDescription(description);
            this.description = description;
            return String.format(SuccessMessages.RECIPE_SET_DESC);
        } catch (InvalidLengthException e) {
            return String.format(FailMessages.RECIPE_INVALID_DESCRIPTION_LENGTH);
        }
    }

    public String setInstructions(String instructions) {
        try {
            validateInstructions(instructions);
            this.instructions = instructions;
            return String.format(SuccessMessages.RECIPE_SET_INSTRUCTIONS);
        } catch (InvalidLengthException e) {
            return String.format(FailMessages.RECIPE_INVALID_INSTRUCTIONS_LENGTH);
        }
    }

    public String setTags(Set<Tag> tags) {
        try {
            validateTags(tags);
            this.tags = tags;
            return String.format(SuccessMessages.RECIPE_SET_TAGS);
        } catch (InvalidCountException e) {
            return String.format(FailMessages.RECIPE_INVALID_TAGS_COUNT);
        }
    }

    public String setIngredients(Map<Ingredient, Integer> ingredients) {
        try {
            validateIngredients(ingredients);
            this.ingredients = ingredients;
            return String.format(SuccessMessages.RECIPE_SET_INGREDIENTS);
        } catch (InvalidCountException e) {
            return String.format(FailMessages.RECIPE_INVALID_INGREDIENTS_COUNT);
        }
    }

    public String setServingSize(byte servingSize) {
        try {
            validateServingSize(servingSize);
            this.servingSize = servingSize;
            return String.format(SuccessMessages.RECIPE_SET_SERVING_SIZE, servingSize);
        } catch (InvalidCountException e) {
            return String.format(FailMessages.RECIPE_INVALID_SERVING_SIZE);
        }
    }

    private String setAuthor(UUID authorId) {
        try {
            validateAuthor(authorId);
            this.authorId = authorId;
            return String.format(SuccessMessages.RECIPE_SET_AUTHOR);
        } catch (InvalidInstanceException e) {
            return String.format(FailMessages.USER_NOT_EXIST);
        }
    }
    
    private void setComments(Set<UUID> comments) {
        this.commentsIds = commentsIds;
    }



    // VALIDATORS
    private void validateName(String name) throws InvalidLengthException {
        Validator.validateStringLength(name,
                Variables.MIN_TAG_NAME_LENGTH, Variables.MAX_TAG_NAME_LENGTH);

    }

    private void validateDescription(String description) throws InvalidLengthException {
        Validator.validateStringLength(description, Variables.MIN_RECIPE_DESC_LENGTH,
                Variables.MAX_RECIPE_DESC_LENGTH);
    }
    
    private void validateInstructions(String instructions)
            throws InvalidLengthException {
        Validator.validateStringLength(instructions, Variables.MIN_RECIPE_INSTRUCTIONS_LENGTH,
                Variables.MAX_RECIPE_INSTRUCTIONS_LENGTH);
    }

    private void validateTags(Set<Tag> tags) throws InvalidCountException {
        Validator.validateCount(tags.size(),
                Variables.MIN_RECIPE_TAGS, Variables.MAX_RECIPE_TAGS);
    }

    private void validateIngredients(Map<Ingredient, Integer> ingredients)
            throws InvalidCountException {
        Validator.validateCount(ingredients.size(),
                Variables.MIN_RECIPE_INGREDIENTS, Variables.MAX_RECIPE_INGREDIENTS);
    }

    private void validateServingSize(byte servingSize) throws InvalidCountException {
        Validator.validateCount(servingSize, Variables.MIN_RECIPE_SERVING_SIZE,
                Variables.MAX_RECIPE_SERVING_SIZE);
    }

    private void validateAuthor(UUID authorId) throws InvalidInstanceException {
        Validator.validateUser(authorId);
    }

}
