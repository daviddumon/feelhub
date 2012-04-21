package com.steambeat.domain.subject.concept;

import com.steambeat.domain.analytics.alchemy.readmodel.*;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsConceptFactory {

    @Test
    public void canCreateFromNamedEntity() {
        final ConceptFactory conceptFactory = new ConceptFactory();
        final List<AlchemyJsonEntity> entities = TestFactories.alchemy().entities(1);
        final AlchemyJsonEntity entity = entities.get(0);

        final Concept concept = conceptFactory.newConcept(entity);

        assertThat(concept.getLanguage(), is("english"));
        assertThat(concept.getType(), is(entity.type));
        assertThat(concept.getRelevance(), is(entity.relevance));
        assertThat(concept.getCount(), is(entity.count));
        assertThat(concept.getText(), is(entity.text));
        final AlchemyJsonDisambiguated disambiguated = entity.disambiguated;
        assertThat(concept.getName(), is(disambiguated.name));
        assertThat(concept.getSubTypes().size(), is(3));
        assertThat(concept.getSubTypes().get(0), is("subtype1"));
        assertThat(concept.getWebsite(), is(new Uri(disambiguated.website).toString()));
        assertThat(concept.getGeo(), is(disambiguated.geo));
        assertThat(concept.getDbpedia(), is(new Uri(disambiguated.dbpedia).toString()));
        assertThat(concept.getYago(), is(new Uri(disambiguated.yago).toString()));
        assertThat(concept.getOpencyc(), is(new Uri(disambiguated.opencyc).toString()));
        assertThat(concept.getUmbel(), is(new Uri(disambiguated.umbel).toString()));
        assertThat(concept.getFreebase(), is(new Uri(disambiguated.freebase).toString()));
        assertThat(concept.getCiaFactbook(), is(new Uri(disambiguated.ciaFactbook).toString()));
        assertThat(concept.getCensus(), is(Uri.empty().toString()));
        assertThat(concept.getGeonames(), is(new Uri(disambiguated.geonames).toString()));
        assertThat(concept.getMusicBrainz(), is(new Uri(disambiguated.musicBrainz).toString()));
        assertThat(concept.getCrunchbase(), is(new Uri(disambiguated.crunchbase).toString()));
        assertThat(concept.getSemanticCrunchbase(), is(new Uri(disambiguated.semanticCrunchbase).toString()));
    }
}
