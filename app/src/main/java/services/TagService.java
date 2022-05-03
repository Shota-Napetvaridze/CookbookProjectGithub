package cookbook.services;

import cookbook.models.entities.Tag;

public interface TagService {
    public String addTag(String name);
    public String changeTagName(Tag tag, String name);
    public String removeTag(Tag tag);
}
