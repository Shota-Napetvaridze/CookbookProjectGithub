package cookbook.util.exceptions.recipe;

import cookbook.util.constants.FailMessages;

/**
 * Exception for invalid member email.
 */
public class InexistentRecipeException extends Exception {
    public InexistentRecipeException() {
        super(String.format(FailMessages.RECIPE_NOT_EXIST));

    }
}