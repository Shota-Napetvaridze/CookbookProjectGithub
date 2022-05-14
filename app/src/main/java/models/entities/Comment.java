package models.entities;

import java.util.UUID;
import util.common.Validator;
import util.constants.FailMessages;
import util.constants.SuccessMessages;
import util.constants.Variables;
import util.exceptions.common.InvalidInstanceException;
import util.exceptions.common.InvalidLengthException;

public class Comment extends BaseEntity {
    private UUID userId;
    private String text;
    private UUID recipeId;

    // Creating new Comment
    // public Comment(UUID userId, String text, UUID recipeId) {
    //     super();
    //     setUser(userId);
    //     setText(text);
    //     setRecipe(recipeId);
    // }

    // Importing an existing Comment
    public Comment(UUID id, UUID userId, String text, UUID recipeId) {
        super.id = id;
        this.userId = userId;
        this.text = text;
        this.recipeId = recipeId;
    }

    // OPERATIONS



    // GETTERS
    public UUID getUser() {
        return userId;
    }

    public String getText() {
        return text;
    }

    public UUID getRecipe() {
        return this.recipeId;
    }



    // SETTERS
    public String setText(String text) {
        try {
            validateText(text);
            this.text = text;
            return String.format(SuccessMessages.COMMENT_SET_TEXT);
        } catch (InvalidLengthException e) {
            return String.format(FailMessages.COMMENT_INVALID_TEXT_LENGTH);
        }
    }

    private String setUser(UUID userId) {
        try {
            validateUser(userId);
            this.userId = userId;
            return String.format(SuccessMessages.COMMENT_SET_USER);
        } catch (InvalidInstanceException e) {
            return String.format(FailMessages.USER_NOT_EXIST);
        }
    }

    private String setRecipe(UUID recipeId) {
        try {
            validateRecipe(recipeId);
            this.recipeId = recipeId;
            return String.format(SuccessMessages.COMMENT_SET_RECIPE);
        } catch (InvalidInstanceException e) {
            return String.format(FailMessages.RECIPE_NOT_EXIST);
        }
    }



    // VALIDATORS
    private void validateText(String text) throws InvalidLengthException {
        Validator.validateStringLength(text, Variables.MIN_COMMENT_TEXT_LENGTH,
                Variables.MAX_COMMENT_TEXT_LENGTH);
    }

    private void validateUser(UUID userId) throws InvalidInstanceException {
        Validator.validateUser(userId);
    }

    private void validateRecipe(UUID recipeId) throws InvalidInstanceException {
        Validator.validateRecipe(recipeId);
    }
}
