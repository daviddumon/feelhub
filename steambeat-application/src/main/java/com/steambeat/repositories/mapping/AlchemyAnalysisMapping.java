package com.steambeat.repositories.mapping;

import com.steambeat.domain.alchemy.AlchemyAnalysis;
import org.mongolink.domain.mapper.EntityMap;

public class AlchemyAnalysisMapping extends EntityMap<AlchemyAnalysis> {

    public AlchemyAnalysisMapping() {
        super(AlchemyAnalysis.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getReferenceId());
        property(element().getValue());
        property(element().getLanguageCode());
    }
}
