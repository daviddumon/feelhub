package com.feelhub.repositories.mapping;

import com.feelhub.domain.tag.TagItem;
import org.mongolink.domain.mapper.ComponentMap;

public class TagItemMapping extends ComponentMap<TagItem> {

    public TagItemMapping() {
        super(TagItem.class);
    }

    @Override
    protected void map() {
        property(element().getId());
        property(element().getLanguageCode());
    }
}
