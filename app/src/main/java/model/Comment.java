package model;

import java.util.UUID;

import util.common.Validator;
import util.constants.FailMessages;
import util.constants.SuccessMessages;
import util.constants.Variables;
import util.exceptions.common.InvalidInstanceException;
import util.exceptions.common.InvalidLengthException;

public class Comment extends BaseEntity {

    private User user;
    private String text;
    private Recipe recipe;

    // Creating new Comment
    public Comment(User user, String text, Recipe recipe) {
        super();
//        setUser(user);
//        setText(text);
//        setRecipe(recipe);
    }

    // Importing an existing Comment
    public Comment(UUID id, User user, String text, Recipe recipe) {
        super.id = id;
        this.user = user;
        this.text = text;
        this.recipe = recipe;
    }

    // OPERATIONS



    // GETTERS
    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public Recipe getRecipe() {
        return this.recipe;
    }



    // SETTERS
//    public String setText(String text) {
//        try {
//            validateText(text);
//            this.text = text;
//            return String.format(SuccessMessages.COMMENT_SET_TEXT);
//        } catch (InvalidLengthException e) {
//            return String.format(FailMessages.COMMENT_INVALID_TEXT_LENGTH);
//        }
//    }

//    private String setUser(User user) {
//        try {
//            validateUser(user);
//            this.user = user;
//            return String.format(SuccessMessages.COMMENT_SET_USER);
//        } catch (InvalidInstanceException e) {
//            return String.format(FailMessages.USER_NOT_EXIST, user.getNickname());
//        }
//    }

//    private String setRecipe(Recipe recipe) {
//        try {
//            validateRecipe(recipe);
//            this.recipe = recipe;
//            return String.format(SuccessMessages.COMMENT_SET_RECIPE);
//        } catch (InvalidInstanceException e) {
//            return String.format(FailMessages.RECIPE_NOT_EXIST);
//        }
//    }



    // VALIDATORS
    private void validateText(String text) throws InvalidLengthException {
        Validator.validateStringLength(text, Variables.MIN_COMMENT_TEXT_LENGTH,
                Variables.MAX_COMMENT_TEXT_LENGTH);
    }

    private void validateUser(User user) throws InvalidInstanceException {
        Validator.validateExists(user);
    }

//    private void validateRecipe(Recipe recipe) throws InvalidInstanceException {
//        Validator.validateExists(recipe);
//    }

}
