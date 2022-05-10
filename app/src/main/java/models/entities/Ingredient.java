package models.entities;

import java.util.UUID;

import models.entities.enums.Unit;
import util.common.Validator;
import util.constants.FailMessages;
import util.constants.SuccessMessages;
import util.constants.Variables;
import util.exceptions.common.InvalidLengthException;
import util.exceptions.ingredient.InvalidUnitException;
import util.exceptions.ingredient.TakenIngredientName;

public class Ingredient extends BaseEntity {

    private String name;
    private Unit unit;

    public Ingredient(String name, Unit unit) {
        super();
        setName(name);
        setUnit(unit);
    }

    public Ingredient(UUID id, String name, Unit unit) {
        super.id = id;
        this.name = name;
        this.unit = unit;
    }



    // GETTERS
    public String getName() {
        return name;
    }

    public Unit getUnit() {
        return unit;
    }



    // SETTERS
    private String setName(String name) {
        try {
            validateName(name);
            this.name = name;
            return String.format(SuccessMessages.INGREDIENT_SET_NAME);
        } catch (InvalidLengthException e) {
            return String.format(FailMessages.INGREDIENT_INVALID_NAME_LENGTH);
        } catch (TakenIngredientName e) {
            return String.format(FailMessages.INGREDIENT_NAME_TAKEN, name);
        }
    }

    private String setUnit(Unit unit) {
        try {
            validateUnit(unit);
            this.unit = unit;
            return String.format(SuccessMessages.INGREDIENT_SET_UNIT);
        } catch (InvalidUnitException e) {
            return String.format(FailMessages.INGREDIENT_UNIT_NOT_EXIST);
        }
    }


    
    // VALIDATORS
    private void validateName(String name) throws InvalidLengthException, TakenIngredientName {
        Validator.validateStringLength(name, Variables.MIN_INGREDIENT_NAME_LENGTH,
                Variables.MAX_INGREDIENT_NAME_LENGTH);

        if (!isUnique(name)) {
            throw new TakenIngredientName(name);
        }
    }

    private void validateUnit(Unit unit) throws InvalidUnitException {
        if (unit == null) {
            throw new InvalidUnitException();
        }
    }

    private boolean isUnique(String name) {
        if (name == "taken") {
            return false;
        }
        return true;
    }

}
