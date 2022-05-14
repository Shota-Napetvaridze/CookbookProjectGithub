package util.common;

import java.util.UUID;

import util.constants.Variables;
import util.exceptions.common.InvalidCountException;
import util.exceptions.common.InvalidInstanceException;
import util.exceptions.common.InvalidLengthException;
import util.exceptions.user.InvalidPasswordComplexityException;
import util.exceptions.user.InvalidPasswordLengthException;

public class Validator {
    private static DbContext dbContext = new DbContext(Variables.DATABASE_PORT, Variables.DATABASE_USER, Variables.DATABASE_PASS);
    /**
     * Checks if a string is within given length.
     *
     * @param string    string
     * @param minLength min length
     * @param maxLength max length
     * @throws InvalidLengthException out of range
     */
    public static void validateStringLength(String string, int minLength, int maxLength)
            throws InvalidLengthException {
        if (string.length() < minLength
                || string.length() > maxLength) {
            throw new InvalidLengthException();
        }
    }

    /**
     * Checks if count is in a given range.
     *
     * @param count    current count
     * @param minCount min count
     * @param maxCount max count
     * @throws InvalidCountException out of range
     */
    public static void validateCount(int count, int minCount, int maxCount)
            throws InvalidCountException {
        if (count < minCount
                || count > maxCount) {
            throw new InvalidCountException();
        }
    }

    /**
     * Checks if a user exists in the database.
     *
     * @param entity user
     * @throws InvalidInstanceException not in database
     */
    public static void validateUser(UUID id) throws InvalidInstanceException {
        if (dbContext.getUserById(id) == null) {
            throw new InvalidInstanceException();
        }
    }

    public static void validateRecipe(UUID id) throws InvalidInstanceException {
        if (dbContext.getRecipeById(id) == null) {
            throw new InvalidInstanceException();
        }
    }

    public static void validatePassword(String password) throws InvalidPasswordLengthException, InvalidPasswordComplexityException {
        try {
            validateStringLength(password, Variables.MIN_PASSWORD_LENGTH, Variables.MAX_PASSWORD_LENGTH);
        } catch (InvalidLengthException e) {
            throw new InvalidPasswordLengthException();
        }
        validatePasswordComplexity(password);
    }

    private static void validatePasswordComplexity(String password) throws InvalidPasswordComplexityException {
        boolean hasLowerCase = false;
        boolean hasUpperCase = false;
        boolean hasDigit = false;
        for (int i = 0; i < password.length(); i++) { // Check for lowercase, uppercase and digit
            Character currChar = password.charAt(i);
            System.out.println(currChar);
            if (Character.isLowerCase(currChar)
                    && !hasLowerCase) {
                hasLowerCase = true;
            } else if (Character.isUpperCase(currChar)
                    && !hasUpperCase) {
                hasUpperCase = true;
            } else if (Character.isDigit(currChar)
                    && !hasDigit) {
                hasDigit = true;
            }
            if (hasLowerCase && hasUpperCase && hasDigit) {
                break;
            }

            }
            if (!hasLowerCase
            || !hasUpperCase
            || !hasDigit) {
        throw new InvalidPasswordComplexityException();
        }
    }
}
