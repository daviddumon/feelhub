package com.steambeat.domain.analytics.alchemy;

import com.alchemyapi.api.*;
import com.google.common.collect.Lists;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.tools.SteambeatApplicationProperties;
import org.w3c.dom.*;

import java.util.List;

public class AlchemyNamedEntityProvider implements NamedEntityProvider {

    public AlchemyNamedEntityProvider(final AlchemyAPI alchemyAPI) {
        this.alchemyAPI = alchemyAPI;
        final SteambeatApplicationProperties steambeatApplicationProperties = new SteambeatApplicationProperties();
        apiKey = steambeatApplicationProperties.getAlchemyApiKey();
        this.alchemyAPI.SetAPIKey(apiKey);
    }

    @Override
    public List<NamedEntity> entitiesFor(final WebPage webpage) {
        List<NamedEntity> result = Lists.newArrayList();
        final AlchemyAPI_NamedEntityParams params = new AlchemyAPI_NamedEntityParams();
        params.setDisambiguate(true);
        params.setLinkedData(true);
        params.setCoreference(false);
        params.setQuotations(false);
        params.setSentiment(false);
        try {
            final Document document = alchemyAPI.URLGetRankedNamedEntities(webpage.getUri(), params);
            result = getEntitiesFrom(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<NamedEntity> getEntitiesFrom(final Document document) {
        List<NamedEntity> results = Lists.newArrayList();
        final NodeList entities = document.getElementsByTagName("entity");
        for (int i = 0; i < entities.getLength(); i++) {
            results.add(new NamedEntity());
        }
        return results;
    }

    public String getApiKey() {
        return apiKey;
    }

    private String apiKey;
    private final AlchemyAPI alchemyAPI;
}
