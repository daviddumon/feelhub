package com.feelhub.repositories.mapping;

import com.feelhub.domain.alchemy.AlchemyAnalysis;
import org.mongolink.domain.mapper.AggregateMap;

public class AlchemyAnalysisMapping extends AggregateMap<AlchemyAnalysis> {

    public AlchemyAnalysisMapping() {
        super(AlchemyAnalysis.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getTopicId());
        property(element().getValue());
        property(element().getLanguageCode());
    }
}
