package services.impl;

import models.entities.Ingredient;
import services.IngredientService;
import util.common.DbContext;
import util.constants.Variables;

public class IngredientServiceImpl implements IngredientService {
    private DbContext dbContext;

    public IngredientServiceImpl() {
        super();
        dbContext = new DbContext(Variables.DATABASE_PORT, Variables.DATABASE_USER, Variables.DATABASE_PASS);
    }

    @Override
    public Ingredient getIngredientByName(String name) {
        return dbContext.getIngredientByName(name);
    }
    
}
