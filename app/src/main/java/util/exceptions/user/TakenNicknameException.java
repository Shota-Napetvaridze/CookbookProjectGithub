package cookbook.util.exceptions.user;

import cookbook.util.constants.FailMessages;

/**
 * Exception for invalid member email.
 */
public class TakenNicknameException extends Exception {
  public TakenNicknameException(String nickname) {
    super(String.format(FailMessages.USER_NICK_TAKEN, nickname));
  }
}