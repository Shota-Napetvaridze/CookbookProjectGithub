package services;

import java.util.List;

import models.entities.Tag;

public interface TagService {
    public List<Tag> getAllTags();
    public String addTag(String name);
    public String changeTagName(Tag tag, String name);
    public String removeTag(Tag tag);
}
