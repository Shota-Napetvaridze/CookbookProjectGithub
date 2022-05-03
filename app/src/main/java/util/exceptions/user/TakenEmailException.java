package cookbook.util.exceptions.user;

import cookbook.util.constants.FailMessages;

/**
 * Exception for invalid member email.
 */
public class TakenEmailException extends Exception {
  public TakenEmailException() {
    super(String.format(FailMessages.USER_EMAIL_TAKEN));
  }
}