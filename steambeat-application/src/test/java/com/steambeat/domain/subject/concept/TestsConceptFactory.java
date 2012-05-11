package com.steambeat.domain.subject.concept;

import com.steambeat.domain.alchemy.readmodel.*;
import com.steambeat.domain.association.uri.Uri;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsConceptFactory {

    @Test
    public void canUseNameIfAvailable() {
        final ConceptFactory conceptFactory = new ConceptFactory();
        final List<AlchemyJsonEntity> entities = TestFactories.alchemy().entities(1);
        final AlchemyJsonEntity entity = entities.get(0);

        final Concept concept = conceptFactory.newConcept(entity);

        assertThat(concept.getShortDescription(), is(entity.disambiguated.name));
        assertThat(concept.getDescription(), is(entity.disambiguated.name));
    }

    @Test
    public void canUseTextIfNameUnavailable() {
        final ConceptFactory conceptFactory = new ConceptFactory();
        final List<AlchemyJsonEntity> entities = TestFactories.alchemy().entitiesWithoutDisambiguated(1);
        final AlchemyJsonEntity entity = entities.get(0);

        final Concept concept = conceptFactory.newConcept(entity);

        assertThat(concept.getShortDescription(), is(entity.text));
    }

    @Test
    public void canCreateFromNamedEntity() {
        final ConceptFactory conceptFactory = new ConceptFactory();
        final List<AlchemyJsonEntity> entities = TestFactories.alchemy().entities(1);
        final AlchemyJsonEntity entity = entities.get(0);

        final Concept concept = conceptFactory.newConcept(entity);

        final AlchemyJsonDisambiguated disambiguated = entity.disambiguated;
        assertThat(concept.getLanguage(), is("english"));
        assertThat(concept.getType(), is(entity.type));
        assertThat(concept.getShortDescription(), is(disambiguated.name));
        assertThat(concept.getSubTypes().size(), is(3));
        assertThat(concept.getSubTypes().get(0), is("subtype1"));
        assertThat(concept.getWebsite(), is(new Uri(disambiguated.website).toString()));
        assertThat(concept.getGeo(), is(disambiguated.geo));
    }
}
