package cookbook.util.exceptions.recipe;

import cookbook.util.constants.FailMessages;

/**
 * Exception for invalid member email.
 */
public class InvalidRecipeTagsCountException extends Exception {
  public InvalidRecipeTagsCountException() {
    super(String.format(FailMessages.RECIPE_INVALID_TAGS_COUNT));
  }
}