package cookbook.util.exceptions.common;

import cookbook.util.constants.FailMessages;

/**
 * Exception for invalid member email.
 */
public class InvalidLengthException extends Exception {
  public InvalidLengthException() {
    super(String.format(FailMessages.INVALID_LENGTH));
  }
}