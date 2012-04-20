package com.steambeat.domain.analytics.alchemy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.steambeat.domain.analytics.alchemy.readmodel.*;
import com.steambeat.domain.subject.webpage.WebPage;

import java.io.*;
import java.util.List;

public class AlchemyJsonEntityProvider implements AlchemyEntityProvider {

    public AlchemyJsonEntityProvider(final AlchemyLink alchemyLink) {
        this.alchemyLink = alchemyLink;
    }

    @Override
    public List<AlchemyJsonEntity> entitiesFor(final WebPage webpage) {
        try {
            final InputStream stream = alchemyLink.get(webpage.getUri());
            return unmarshall(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<AlchemyJsonEntity> unmarshall(final InputStream stream) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final AlchemyJsonResults alchemyJsonResults = objectMapper.readValue(stream, AlchemyJsonResults.class);
            for (AlchemyJsonEntity entity : alchemyJsonResults.entities) {
                entity.language = alchemyJsonResults.language;
            }
            return alchemyJsonResults.entities;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private AlchemyLink alchemyLink;
}
