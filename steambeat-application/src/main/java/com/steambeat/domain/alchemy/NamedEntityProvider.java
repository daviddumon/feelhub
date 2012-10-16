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
            throw new AlchemyException();
        }
    }

    private List<NamedEntity> unmarshall(final InputStream stream) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final List<NamedEntity> results = Lists.newArrayList();
        final AlchemyJsonResults alchemyJsonResults = objectMapper.readValue(stream, AlchemyJsonResults.class);
        if (alchemyJsonResults.status.equalsIgnoreCase("error")) {
            throw new IOException();
        } else {
            return getResults(results, alchemyJsonResults);
        }
    }

    private List<NamedEntity> getResults(final List<NamedEntity> results, final AlchemyJsonResults alchemyJsonResults) {
        for (final AlchemyJsonEntity entity : alchemyJsonResults.entities) {
            entity.language = alchemyJsonResults.language;
            final NamedEntity namedEntity = namedEntityBuilder.build(entity);
            if (namedEntity != null) {
                results.add(namedEntity);
            }
        }
        return results;
    }

    private AlchemyLink alchemyLink;
    private NamedEntityBuilder namedEntityBuilder;
}
