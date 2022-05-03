package cookbook.util.exceptions.recipe;

import cookbook.util.constants.FailMessages;

/**
 * Exception for invalid member email.
 */
public class InvalidRecipeNameLengthException extends Exception {
  public InvalidRecipeNameLengthException() {
    super(String.format(FailMessages.RECIPE_INVALID_NAME_LENGTH));
  }
}