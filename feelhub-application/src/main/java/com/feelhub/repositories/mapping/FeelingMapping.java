package com.feelhub.repositories.mapping;

import com.feelhub.domain.feeling.Feeling;
import org.mongolink.domain.mapper.EntityMap;

public class FeelingMapping extends EntityMap<Feeling> {

    public FeelingMapping() {
        super(Feeling.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getUserId());
        property(element().getText());
        property(element().getLanguageCode());
        collection(element().getSentiments());
        property(element().getCreationDate());
        property(element().getLastModificationDate());
    }
}
