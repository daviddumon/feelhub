package com.steambeat.domain.alchemy;

import com.google.common.collect.Lists;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.Repositories;

import java.util.*;

public class AlchemyTestFactory {

    public Alchemy newAlchemy() {
        final Reference reference = createAndPersistReference();
        return newAlchemy(reference.getId());
    }

    public Alchemy newAlchemy(final UUID referenceId) {
        final Alchemy alchemy = new Alchemy(referenceId);
        alchemy.setCensus("census");
        alchemy.setCiafactbook("ciafactbook");
        alchemy.setCrunchbase("crunchbase");
        alchemy.setDbpedia("dbpedia");
        alchemy.setFreebase("freebase");
        alchemy.setGeo("geo");
        alchemy.setGeonames("geonames");
        alchemy.setMusicbrainz("musicbrainz");
        alchemy.setOpencyc("opencyc");
        alchemy.setSemanticcrunchbase("crunchbase");
        List<String> subTypes = Lists.newArrayList();
        subTypes.add("sub1");
        subTypes.add("sub2");
        alchemy.setSubtype(subTypes);
        alchemy.setType("type");
        alchemy.setUmbel("umbel");
        alchemy.setWebsite("website");
        alchemy.setYago("yago");
        Repositories.alchemys().add(alchemy);
        return alchemy;
    }

    private Reference createAndPersistReference() {
        final UUID id = UUID.randomUUID();
        final Reference reference = new Reference(id);
        Repositories.references().add(reference);
        return reference;
    }
}
