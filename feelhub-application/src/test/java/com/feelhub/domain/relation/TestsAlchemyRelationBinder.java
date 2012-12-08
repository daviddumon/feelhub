package com.feelhub.domain.relation;

import com.feelhub.domain.topic.usable.real.RealTopic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.common.collect.Maps;
import com.google.inject.*;
import org.junit.*;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsAlchemyRelationBinder {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        alchemyRelationBinder = injector.getInstance(AlchemyRelationBinder.class);
    }

    @Test
    public void canCreateRelations() {
        final RealTopic mainRealTopic = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        final HashMap<UUID, Double> topicIds = Maps.newHashMap();
        topicIds.put(realTopic1.getId(), 0.2);
        topicIds.put(realTopic2.getId(), 0.6);

        alchemyRelationBinder.bind(mainRealTopic.getId(), topicIds);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(6));
        final Relation relation1 = Repositories.relations().lookUp(mainRealTopic.getId(), realTopic1.getId());
        assertThat(relation1.getWeight(), is(1.2));
        final Relation relation2 = Repositories.relations().lookUp(mainRealTopic.getId(), realTopic2.getId());
        assertThat(relation2.getWeight(), is(1.6));
    }

    private AlchemyRelationBinder alchemyRelationBinder;
}



