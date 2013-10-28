package com.feelhub.repositories.mapping;

import com.feelhub.domain.alchemy.AlchemyAnalysis;
import org.mongolink.domain.mapper.AggregateMap;

public class AlchemyAnalysisMapping extends AggregateMap<AlchemyAnalysis> {

    public AlchemyAnalysisMapping() {
        super(AlchemyAnalysis.class);
    }

    @Override
    public void map() {
        id().onProperty(element().getId()).natural();
        property().onProperty(element().getTopicId());
        property().onProperty(element().getValue());
        property().onProperty(element().getLanguageCode());
    }
}
