package services;

import java.util.List;
import java.util.UUID;

import models.entities.Tag;

public interface TagService {
    public List<Tag> getAllTags();
    public List<Tag> getTagsByRecipeId(UUID recipeId);
    public List<Tag> getTagsWithNameLike(String name);
    public String addTag(String name);
    public String changeTagName(Tag tag, String name);
    public String removeTag(Tag tag);
}
