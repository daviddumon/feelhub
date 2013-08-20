package com.feelhub.repositories.mapping;

import com.feelhub.domain.feeling.Feeling;
import org.mongolink.domain.mapper.AggregateMap;

public class FeelingMapping extends AggregateMap<Feeling> {

    public FeelingMapping() {
        super(Feeling.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getUserId());
        property(element().getTopicId());
        property(element().getFeelingValue());
        property(element().getText());
        property(element().getLanguageCode());
        property(element().getCreationDate());
        property(element().getLastModificationDate());
    }
}
