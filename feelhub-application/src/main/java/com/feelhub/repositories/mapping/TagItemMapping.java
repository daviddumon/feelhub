package com.feelhub.repositories.mapping;

import com.feelhub.domain.tag.TagItem;
import org.mongolink.domain.mapper.ComponentMap;

public class TagItemMapping extends ComponentMap<TagItem> {

    public TagItemMapping() {
        super(TagItem.class);
    }

    @Override
    public void map() {
        property().onProperty(element().getId());
        property().onProperty(element().getLanguageCode());
    }
}
