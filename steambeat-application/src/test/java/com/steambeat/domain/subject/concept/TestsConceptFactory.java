package com.steambeat.domain.subject.concept;

import com.steambeat.domain.alchemy.NamedEntity;
import com.steambeat.domain.association.uri.Uri;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsConceptFactory {

    @Before
    public void before() {
        conceptFactory = new ConceptFactory();
    }

    @Test
    public void canCreateFromNamedEntity() {
        final NamedEntity entity = TestFactories.alchemy().namedEntityWith2Keywords().get(0);

        final Concept concept = conceptFactory.newConcept(entity);

        assertThat(concept.getId(), is(entity.conceptId));
        assertThat(concept.getType(), is(entity.type));
        assertThat(concept.getShortDescription(), is(entity.name));
        assertThat(concept.getDescription(), is(entity.name));
        assertThat(concept.getSubTypes().size(), is(3));
        assertThat(concept.getSubTypes().get(0), is("subtype1"));
        assertThat(concept.getWebsite(), is(new Uri(entity.website).toString()));
        assertThat(concept.getGeo(), is(entity.geo));
    }

    private ConceptFactory conceptFactory;
}
