package com.steambeat.domain.alchemy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.domain.alchemy.readmodel.*;

import java.io.*;
import java.util.List;

public class NamedEntityProvider {

    @Inject
    public NamedEntityProvider(final AlchemyLink alchemyLink, final NamedEntityBuilder namedEntityBuilder) {
        this.alchemyLink = alchemyLink;
        this.namedEntityBuilder = namedEntityBuilder;
    }

    public List<NamedEntity> entitiesFor(final String uri) {
        try {
            final InputStream stream = alchemyLink.get(uri);
            return unmarshall(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<NamedEntity> unmarshall(final InputStream stream) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final List<NamedEntity> results = Lists.newArrayList();
        try {
            final AlchemyJsonResults alchemyJsonResults = objectMapper.readValue(stream, AlchemyJsonResults.class);
            for (final AlchemyJsonEntity entity : alchemyJsonResults.entities) {
                entity.language = alchemyJsonResults.language;
                results.add(namedEntityBuilder.build(entity));
            }
            return results;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private AlchemyLink alchemyLink;
    private NamedEntityBuilder namedEntityBuilder;
}
