package com.feelhub.repositories.mapping;

import com.feelhub.domain.tag.Tag;
import org.mongolink.domain.mapper.EntityMap;

public class TagMapping extends EntityMap<Tag> {

    public TagMapping() {
        super(Tag.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getValue());
        property(element().getLanguageCode());
        property(element().getTopicId());
        property(element().getCreationDate());
        property(element().getLastModificationDate());
    }
}
