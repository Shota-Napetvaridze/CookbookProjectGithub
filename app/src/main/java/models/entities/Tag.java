package cookbook.models.entities;

import java.util.UUID;

import cookbook.util.common.Validator;
import cookbook.util.constants.FailMessages;
import cookbook.util.constants.SuccessMessages;
import cookbook.util.constants.Variables;
import cookbook.util.exceptions.common.InvalidLengthException;

public class Tag extends BaseEntity {

    private String name;

    // Creating new Tag
    public Tag(String name) {
        super();
        setName(name);
    }

    // Importing existing Tag
    public Tag(UUID id, String name) {
        super.id = id;
        setName(name);
    }

    public String getName() {
        return name;
    }

    private String setName(String name) {
        try {
            validateName(name);
            this.name = name;
            // TODO: DbContext.updateTagName(UUID id, String name);
            return String.format(SuccessMessages.TAG_SET_NAME, name);
        } catch (InvalidLengthException e) {
            return String.format(FailMessages.TAG_INVALID_NAME_LENGTH);
        }
    }

    private void validateName(String name) throws InvalidLengthException {
        Validator.validateStringLength(name,
                Variables.MIN_TAG_NAME_LENGTH, Variables.MAX_TAG_NAME_LENGTH);

    }

}
