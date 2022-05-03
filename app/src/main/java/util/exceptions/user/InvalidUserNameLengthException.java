package cookbook.util.exceptions.user;

import cookbook.util.constants.FailMessages;

/**
 * Exception for invalid member name.
 */
public class InvalidUserNameLengthException extends Exception {
  public InvalidUserNameLengthException() {
    super(String.format(FailMessages.USER_INVALID_NAME_LENGTH));
  }
}