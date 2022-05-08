package model;

import java.util.UUID;

import util.common.Validator;
import util.constants.FailMessages;
import util.constants.SuccessMessages;
import util.constants.Variables;
import util.exceptions.common.InvalidLengthException;

public class Tag extends BaseEntity {
    private String name;

    // Constructor
    public Tag(){}

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

    public String setName(String name) {
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
