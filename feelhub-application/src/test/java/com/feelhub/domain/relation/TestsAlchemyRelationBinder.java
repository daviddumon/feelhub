package com.feelhub.domain.relation;

import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.common.collect.Maps;
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
        final Topic mainTopic = TestFactories.topics().newTopic();
        final Topic topic1 = TestFactories.topics().newTopic();
        final Topic topic2 = TestFactories.topics().newTopic();
        final HashMap<UUID, Double> topicIds = Maps.newHashMap();
        topicIds.put(topic1.getId(), 0.2);
        topicIds.put(topic2.getId(), 0.6);

        alchemyRelationBinder.bind(mainTopic.getId(), topicIds);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(6));
        final Relation relation1 = Repositories.relations().lookUp(mainTopic.getId(), topic1.getId());
        assertThat(relation1.getWeight(), is(1.2));
        final Relation relation2 = Repositories.relations().lookUp(mainTopic.getId(), topic2.getId());
        assertThat(relation2.getWeight(), is(1.6));
    }

    private AlchemyRelationBinder alchemyRelationBinder;
}



