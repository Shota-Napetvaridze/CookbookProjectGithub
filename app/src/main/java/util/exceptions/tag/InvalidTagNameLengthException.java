package cookbook.util.exceptions.tag;

import cookbook.util.constants.FailMessages;

/**
 * Exception for invalid member email.
 */
public class InvalidTagNameLengthException extends Exception {
  public InvalidTagNameLengthException() {
    super(String.format(FailMessages.TAG_INVALID_NAME_LENGTH));
  }
}