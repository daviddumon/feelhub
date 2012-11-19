package com.feelhub.domain.alchemy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.feelhub.domain.alchemy.readmodel.*;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.io.*;
import java.util.List;

public class NamedEntityProvider {

    @Inject
    public NamedEntityProvider(final AlchemyLink alchemyLink, final NamedEntityFactory namedEntityFactory) {
        this.alchemyLink = alchemyLink;
        this.namedEntityFactory = namedEntityFactory;
    }

    public List<NamedEntity> entitiesFor(final Tag tag) {
        try {
            final InputStream stream = alchemyLink.get(tag.getValue());
            final AlchemyJsonResults results = unmarshall(stream);
            createAlchemyAnalysis(tag, results);
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
            final NamedEntity namedEntity = namedEntityFactory.build(entity);
            if (namedEntity != null) {
                results.add(namedEntity);
            }
        }
        return results;
    }

    private void createAlchemyAnalysis(final Tag uri, final AlchemyJsonResults results) {
        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(uri);
        alchemyAnalysis.setLanguageCode(FeelhubLanguage.fromCountryName(results.language));
        Repositories.alchemyAnalysis().add(alchemyAnalysis);
    }

    private final AlchemyLink alchemyLink;
    private final NamedEntityFactory namedEntityFactory;
}
