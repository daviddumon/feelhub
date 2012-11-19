package com.feelhub.repositories.mapping;

import com.feelhub.domain.meta.Illustration;
import org.mongolink.domain.mapper.EntityMap;

public class IllustrationMapping extends EntityMap<Illustration> {

    public IllustrationMapping() {
        super(Illustration.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getTopicId());
        property(element().getLink());
        property(element().getCreationDate());
        property(element().getLastModificationDate());
    }
}
