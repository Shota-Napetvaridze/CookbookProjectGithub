package cookbook.util.exceptions.common;

import cookbook.util.constants.FailMessages;

/**
 * Exception for invalid member email.
 */
public class InvalidInstanceException extends Exception {
  public InvalidInstanceException() {
    super(String.format(FailMessages.INVALID_COUNT));
  }
}