package com.feelhub.repositories.mapping;

import com.feelhub.domain.alchemy.AlchemyEntity;
import org.mongolink.domain.mapper.AggregateMap;

public class AlchemyEntityMapping extends AggregateMap<AlchemyEntity> {

    public AlchemyEntityMapping() {
        super(AlchemyEntity.class);
    }

    @Override
    public void map() {
        id().onProperty(element().getId()).natural();
        property().onProperty(element().getTopicId());
        property().onProperty(element().getCreationDate());
        property().onProperty(element().getLastModificationDate());
        property().onProperty(element().getOpencyc());
        property().onProperty(element().getType());
        property().onProperty(element().getUmbel());
        property().onProperty(element().getWebsite());
        property().onProperty(element().getYago());
        property().onProperty(element().getCiafactbook());
        property().onProperty(element().getCensus());
        property().onProperty(element().getCrunchbase());
        property().onProperty(element().getSemanticcrunchbase());
        property().onProperty(element().getDbpedia());
        property().onProperty(element().getFreebase());
        property().onProperty(element().getGeo());
        property().onProperty(element().getGeonames());
        property().onProperty(element().getMusicbrainz());
        collection().onProperty(element().getSubtype());
        property().onProperty(element().getRelevance());
    }
}
