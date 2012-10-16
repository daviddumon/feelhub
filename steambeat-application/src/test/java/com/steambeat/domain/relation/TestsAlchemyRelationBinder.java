package com.steambeat.domain.relation;

import com.google.common.collect.Maps;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import org.junit.*;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsAlchemyRelationBinder {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        alchemyRelationBinder = new AlchemyRelationBinder(new RelationBuilder(new RelationFactory()));
    }

    @Test
    public void canCreateRelations() {
        final Reference mainReference = TestFactories.references().newReference();
        final Reference ref1 = TestFactories.references().newReference();
        final Reference ref2 = TestFactories.references().newReference();
        final HashMap<UUID, Double> referenceIds = Maps.newHashMap();
        referenceIds.put(ref1.getId(), 0.2);
        referenceIds.put(ref2.getId(), 0.6);

        alchemyRelationBinder.bind(mainReference.getId(), referenceIds);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(6));
        final Relation relation1 = Repositories.relations().lookUp(mainReference.getId(), ref1.getId());
        assertThat(relation1.getWeight(), is(1.2));
        final Relation relation2 = Repositories.relations().lookUp(mainReference.getId(), ref2.getId());
        assertThat(relation2.getWeight(), is(1.6));
    }

    private AlchemyRelationBinder alchemyRelationBinder;
}



