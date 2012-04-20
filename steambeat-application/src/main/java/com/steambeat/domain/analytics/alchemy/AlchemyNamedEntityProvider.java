package com.steambeat.domain.analytics.alchemy;

import com.steambeat.domain.analytics.alchemy.readmodel.*;
import com.steambeat.domain.subject.webpage.WebPage;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;
import java.util.List;

public class AlchemyNamedEntityProvider implements NamedEntityProvider {

    public AlchemyNamedEntityProvider(final AlchemyLink alchemyLink) {
        this.alchemyLink = alchemyLink;
    }

    @Override
    public List<AlchemyXmlEntity> entitiesFor(final WebPage webpage) {
        try {
            final InputStream stream = alchemyLink.get(webpage.getUri());
            return unmarshall(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<AlchemyXmlEntity> unmarshall(final InputStream stream) {
        final Persister persister = new Persister();
        try {
            final AlchemyXmlResults results = persister.read(AlchemyXmlResults.class, stream);
            for (AlchemyXmlEntity alchemyXmlEntity : results.entities) {
                alchemyXmlEntity.language = results.language;
            }
            return results.entities;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private AlchemyLink alchemyLink;
}
