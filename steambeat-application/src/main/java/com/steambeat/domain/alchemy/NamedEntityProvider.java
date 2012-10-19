package com.steambeat.domain.alchemy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.domain.alchemy.readmodel.*;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;

import java.io.*;
import java.util.List;

public class NamedEntityProvider {

    @Inject
    public NamedEntityProvider(final AlchemyLink alchemyLink, final NamedEntityBuilder namedEntityBuilder) {
        this.alchemyLink = alchemyLink;
        this.namedEntityBuilder = namedEntityBuilder;
    }

    public List<NamedEntity> entitiesFor(final Keyword keyword) {
        try {
            final InputStream stream = alchemyLink.get(keyword.getValue());
            final AlchemyJsonResults results = unmarshall(stream);
            createAlchemyAnalysis(keyword, results);
            return getResults(results);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AlchemyException();
        }
    }

    private AlchemyJsonResults unmarshall(final InputStream stream) throws IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final AlchemyJsonResults alchemyJsonResults = objectMapper.readValue(stream, AlchemyJsonResults.class);
        if (alchemyJsonResults.status.equalsIgnoreCase("error")) {
            throw new IOException();
        } else {
            return alchemyJsonResults;
        }
    }

    private List<NamedEntity> getResults(final AlchemyJsonResults alchemyJsonResults) {
        final List<NamedEntity> results = Lists.newArrayList();
        for (final AlchemyJsonEntity entity : alchemyJsonResults.entities) {
            entity.language = alchemyJsonResults.language;
            final NamedEntity namedEntity = namedEntityBuilder.build(entity);
            if (namedEntity != null) {
                results.add(namedEntity);
            }
        }
        return results;
    }

    private void createAlchemyAnalysis(final Keyword uri, final AlchemyJsonResults results) {
        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(uri);
        alchemyAnalysis.setLanguageCode(SteambeatLanguage.forString(results.language));
        Repositories.alchemyAnalysis().add(alchemyAnalysis);
    }

    private AlchemyLink alchemyLink;
    private NamedEntityBuilder namedEntityBuilder;
}
