package util.common;

import java.util.UUID;

import model.BaseEntity;
import util.exceptions.common.InvalidCountException;
import util.exceptions.common.InvalidInstanceException;
import util.exceptions.common.InvalidLengthException;

public class Validator {

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
     * Checks if an instance exists in the database.
     *
     * @param entity object instance
     * @throws InvalidInstanceException not in database
     */
    public static void validateExists(BaseEntity entity) throws InvalidInstanceException {
        UUID id = entity.getId();
        String tableName = entity.getClass().getSimpleName() + "s";

        if (id == null) { // if (DbContext.getInstance(id, tableName) == null) {
            throw new InvalidInstanceException();
        }
    }
}
