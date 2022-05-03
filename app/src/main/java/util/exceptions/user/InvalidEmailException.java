package cookbook.util.exceptions.user;

import cookbook.util.constants.FailMessages;

/**
 * Exception for invalid member email.
 */
public class InvalidEmailException extends Exception {
  public InvalidEmailException() {
    super(String.format(FailMessages.USER_INVALID_EMAIL));
  }
}