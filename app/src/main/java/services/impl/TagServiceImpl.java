package services.impl;

import java.util.List;
import java.util.UUID;

import models.entities.Tag;
import services.TagService;
import util.common.DbContext;
import util.constants.Variables;

public class TagServiceImpl implements TagService {
    private DbContext dbContext;

    public TagServiceImpl() {
        super();
        dbContext = new DbContext(Variables.DATABASE_PORT, Variables.DATABASE_USER, Variables.DATABASE_PASS);
    }

    @Override
    public String addTag(String name) {
        return dbContext.addTag(UUID.randomUUID(), name);
    }

    @Override
    public String changeTagName(Tag tag, String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String removeTag(Tag tag) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Tag> getAllTags() {
        return dbContext.getAllTags();
    }

    @Override
    public List<Tag> getTagsByRecipeId(UUID recipeId) {
        return dbContext.getTagsByRecipeId(recipeId);
    }
    
}
