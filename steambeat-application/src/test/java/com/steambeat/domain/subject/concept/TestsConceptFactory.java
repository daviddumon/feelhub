package com.steambeat.domain.subject.concept;

import com.steambeat.domain.alchemy.NamedEntity;
import com.steambeat.domain.uri.Uri;
import com.steambeat.test.FakeBingLink;
import com.steambeat.test.TestFactories;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class TestsConceptFactory {

    @Before
    public void before() {
        conceptFactory = new ConceptFactory(new FakeBingLink());
    }

    @Test
    public void canCreateFromNamedEntity() {
        final NamedEntity entity = TestFactories.alchemy().namedEntityWith2Keywords().get(0);

        final Concept concept = conceptFactory.newConcept(entity);

        assertThat(concept.getType(), is(entity.type));
        assertThat(concept.getSubTypes().size(), is(3));
        assertThat(concept.getSubTypes().get(0), is("subtype1"));
        assertThat(concept.getWebsite(), is(new Uri(entity.website).toString()));
        assertThat(concept.getGeo(), is(entity.geo));
    }

    private ConceptFactory conceptFactory;
}
