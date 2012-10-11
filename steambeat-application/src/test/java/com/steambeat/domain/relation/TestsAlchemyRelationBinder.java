package com.steambeat.domain.relation;

import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.reference.*;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.*;
import com.steambeat.test.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsAlchemyRelationBinder {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        new AlchemyRelationBinder(new FakeSessionProvider(), new RelationBuilder(new RelationFactory()));
    }

    @Test
    public void canCreateRelationBetweenAllConcepts() {
        final ConceptGroupReferencesChangedEvent event = TestFactories.events().newConceptGroupeReferencesChangedEvent();

        DomainEventBus.INSTANCE.post(event);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(30));
    }

    @Test
    public void initialRelationWeightIsRelevanceOfEntityPlusOne() {
        final ConceptGroupReferencesChangedEvent event = TestFactories.events().newConceptGroupeReferencesChangedEvent();
        TestFactories.alchemy().newAlchemy(event.getReferenceId());

        DomainEventBus.INSTANCE.post(event);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.get(0).getWeight(), is(2.0));
    }

    @Test
    public void postAllConceptReferencesChangedEvent() {
        bus.capture(ConceptReferencesChangedEvent.class);
        final ConceptGroupReferencesChangedEvent event = TestFactories.events().newConceptGroupeReferencesChangedEvent();

        DomainEventBus.INSTANCE.post(event);

        final ConceptReferencesChangedEvent conceptReferencesChangedEvent = bus.lastEvent(ConceptReferencesChangedEvent.class);
        assertThat(conceptReferencesChangedEvent, notNullValue());
    }
}



