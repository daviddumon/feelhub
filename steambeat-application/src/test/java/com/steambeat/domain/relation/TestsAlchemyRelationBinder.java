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
        assertThat(relations.get(0).getWeight(), is(1.2));
        assertThat(relations.get(1).getWeight(), is(1.2));
        assertThat(relations.get(2).getWeight(), is(1.6));
        assertThat(relations.get(3).getWeight(), is(1.6));
        assertThat(relations.get(4).getWeight(), is(1.0));
        assertThat(relations.get(5).getWeight(), is(1.0));
    }

    private AlchemyRelationBinder alchemyRelationBinder;
}



