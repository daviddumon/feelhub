package com.feelhub.repositories.mapping;

import com.feelhub.domain.feeling.Feeling;
import org.mongolink.domain.mapper.AggregateMap;

public class FeelingMapping extends AggregateMap<Feeling> {

    public FeelingMapping() {
        super(Feeling.class);
    }

    @Override
    public void map() {
        id().onProperty(element().getId()).natural();
        property().onProperty(element().getUserId());
        property().onProperty(element().getTopicId());
        property().onProperty(element().getFeelingValue());
        property().onProperty(element().getText());
        property().onProperty(element().getLanguageCode());
        property().onProperty(element().getCreationDate());
        property().onProperty(element().getLastModificationDate());
    }
}
