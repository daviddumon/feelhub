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

public class TestsRelationManager {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        new RelationManager(new FakeSessionProvider());
    }

    @Test
    public void canChangeRelationsReferencesForAConcept() {
        final Reference ref1 = TestFactories.references().newReference();
        final Reference ref2 = TestFactories.references().newReference();
        final Reference ref3 = TestFactories.references().newReference();
        final Relation relation1 = TestFactories.relations().newRelation(ref2, ref3);
        final Relation relation2 = TestFactories.relations().newRelation(ref3, ref2);
        final ConceptReferencesChangedEvent event = TestFactories.events().newConceptReferencesChangedEvent(ref1.getId());
        event.addReferenceToChange(ref2.getId());

        DomainEventBus.INSTANCE.post(event);

        assertThat(relation1.getFromId(), is(ref1.getId()));
        assertThat(relation1.getToId(), is(ref3.getId()));
        assertThat(relation2.getFromId(), is(ref3.getId()));
        assertThat(relation2.getToId(), is(ref1.getId()));
    }

    @Test
    public void canRemoveAutoRelation() {
        final Reference ref1 = TestFactories.references().newReference();
        final Reference ref2 = TestFactories.references().newReference();
        TestFactories.relations().newRelation(ref1, ref2);
        TestFactories.relations().newRelation(ref2, ref1);
        final ConceptReferencesChangedEvent event = TestFactories.events().newConceptReferencesChangedEvent(ref1.getId());
        event.addReferenceToChange(ref2.getId());

        DomainEventBus.INSTANCE.post(event);

        assertThat(Repositories.relations().getAll().size(), is(0));
    }

    @Test
    public void canMergeDuplicate() {
        final Reference ref1 = TestFactories.references().newReference();
        final Reference ref2 = TestFactories.references().newReference();
        final Reference ref3 = TestFactories.references().newReference();
        TestFactories.relations().newRelation(ref1, ref2);
        TestFactories.relations().newRelation(ref3, ref2);
        final ConceptReferencesChangedEvent event = TestFactories.events().newConceptReferencesChangedEvent(ref1.getId());
        event.addReferenceToChange(ref3.getId());

        DomainEventBus.INSTANCE.post(event);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(1));
        assertThat(relations.get(0).getWeight(), is(2.0));
    }
}
