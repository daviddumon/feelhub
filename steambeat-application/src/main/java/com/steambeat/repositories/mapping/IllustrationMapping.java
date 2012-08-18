package com.steambeat.repositories.mapping;

import com.steambeat.domain.illustration.Illustration;
import org.mongolink.domain.mapper.EntityMap;

public class IllustrationMapping extends EntityMap<Illustration> {

    public IllustrationMapping() {
        super(Illustration.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getReferenceId());
        property(element().getLink());
        property(element().getCreationDate());
        property(element().getLastModificationDate());
    }
}
