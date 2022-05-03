package cookbook.util.exceptions.user;

import cookbook.util.constants.FailMessages;

/**
 * Exception for invalid member email.
 */
public class InvalidUserException extends Exception {
  public InvalidUserException(String nickname) {
    super(String.format(FailMessages.USER_NOT_EXIST, nickname));
  }
}