package cookbook.util.exceptions.common;

import cookbook.util.constants.FailMessages;

/**
 * Exception for invalid member email.
 */
public class InvalidCountException extends Exception {
  public InvalidCountException() {
    super(String.format(FailMessages.INVALID_COUNT));
  }
}