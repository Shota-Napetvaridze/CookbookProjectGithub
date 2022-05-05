package services;

import models.entities.Tag;

public interface TagService {
    public String addTag(String name);
    public String changeTagName(Tag tag, String name);
    public String removeTag(Tag tag);
}
