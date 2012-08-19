package com.steambeat.domain.relation;

import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.reference.*;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsRelationManager {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        new RelationManager();
    }

    @Test
    public void canChangeReferencesForRelations() {
        final Reference ref1 = TestFactories.references().newReference();
        final Reference ref2 = TestFactories.references().newReference();
        final Relation relation1 = TestFactories.relations().newRelation(ref1, ref2);
        final Relation relation2 = TestFactories.relations().newRelation(ref2, ref1);
        final ReferencesChangedEvent event = new ReferencesChangedEvent(ref1.getId());
        event.addReferenceToChange(ref2.getId());

        DomainEventBus.INSTANCE.post(event);

        assertThat(relation1.getFromId(), is(ref1.getId()));
        assertThat(relation1.getToId(), is(ref1.getId()));
        assertThat(relation2.getFromId(), is(ref1.getId()));
        assertThat(relation2.getToId(), is(ref1.getId()));
    }
}
