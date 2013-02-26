package com.feelhub.domain.alchemy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feelhub.domain.alchemy.readmodel.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.io.*;
import java.util.List;

public class NamedEntityProvider {

    @Inject
    public NamedEntityProvider(final AlchemyLink alchemyLink) {
        this.alchemyLink = alchemyLink;
    }

    public List<NamedEntity> entitiesFor(final HttpTopic topic) {
        try {
            final InputStream stream = alchemyLink.get(topic.getUris().get(0));
            final AlchemyJsonResults results = unmarshall(stream);
            createAlchemyAnalysis(topic, results);
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

    private void createAlchemyAnalysis(final HttpTopic topic, final AlchemyJsonResults results) {
        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(topic);
        alchemyAnalysis.setLanguageCode(FeelhubLanguage.fromCountryName(results.language));
        Repositories.alchemyAnalysis().add(alchemyAnalysis);
    }

    private List<NamedEntity> getResults(final AlchemyJsonResults alchemyJsonResults) {
        final List<NamedEntity> results = Lists.newArrayList();
        for (final AlchemyJsonEntity entity : alchemyJsonResults.entities) {
            entity.language = alchemyJsonResults.language;
            final NamedEntity namedEntity = namedEntityFactory.build(entity);
            if (namedEntity != null) {
                results.add(namedEntity);
            }
        }
        return results;
    }

    private final AlchemyLink alchemyLink;
    private final NamedEntityFactory namedEntityFactory = new NamedEntityFactory();
}
