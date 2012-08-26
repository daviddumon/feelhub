package com.steambeat.repositories.mapping;

import com.steambeat.domain.alchemy.Alchemy;
import org.mongolink.domain.mapper.EntityMap;

public class AlchemyMapping extends EntityMap<Alchemy> {

    public AlchemyMapping() {
        super(Alchemy.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getReferenceId());
        property(element().getCreationDate());
        property(element().getLastModificationDate());
        property(element().getOpencyc());
        property(element().getType());
        property(element().getUmbel());
        property(element().getWebsite());
        property(element().getYago());
        property(element().getCiafactbook());
        property(element().getCensus());
        property(element().getCrunchbase());
        property(element().getSemanticcrunchbase());
        property(element().getDbpedia());
        property(element().getFreebase());
        property(element().getGeo());
        property(element().getGeonames());
        property(element().getMusicbrainz());
        collection(element().getSubtype());
        property(element().getRelevance());
    }
}
