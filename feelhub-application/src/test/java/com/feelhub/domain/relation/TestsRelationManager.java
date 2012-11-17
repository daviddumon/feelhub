package com.feelhub.domain.relation;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
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
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        relationManager = injector.getInstance(RelationManager.class);
    }

    @Test
    public void canChangeRelationsTopics() {
        final Topic topic1 = TestFactories.topics().newTopic();
        final Topic topic2 = TestFactories.topics().newTopic();
        final Topic topic3 = TestFactories.topics().newTopic();
        final Relation relation1 = TestFactories.relations().newRelation(topic2.getId(), topic3.getId());
        final Relation relation2 = TestFactories.relations().newRelation(topic3.getId(), topic2.getId());
        final TopicPatch topicPatch = new TopicPatch(topic1.getId());
        topicPatch.addOldTopicId(topic2.getId());

        relationManager.merge(topicPatch);

        assertThat(relation1.getFromId(), is(topic1.getId()));
        assertThat(relation1.getToId(), is(topic3.getId()));
        assertThat(relation2.getFromId(), is(topic3.getId()));
        assertThat(relation2.getToId(), is(topic1.getId()));
    }

    @Test
    public void canRemoveAutoRelation() {
        final Topic topic1 = TestFactories.topics().newTopic();
        final Topic topic2 = TestFactories.topics().newTopic();
        TestFactories.relations().newRelation(topic1.getId(), topic2.getId());
        TestFactories.relations().newRelation(topic2.getId(), topic1.getId());
        final TopicPatch topicPatch = new TopicPatch(topic1.getId());
        topicPatch.addOldTopicId(topic2.getId());

        relationManager.merge(topicPatch);

        assertThat(Repositories.relations().getAll().size(), is(0));
    }

    @Test
    public void canMergeDuplicate() {
        final Topic topic1 = TestFactories.topics().newTopic();
        final Topic topic2 = TestFactories.topics().newTopic();
        final Topic topic3 = TestFactories.topics().newTopic();
        TestFactories.relations().newRelation(topic1.getId(), topic2.getId());
        TestFactories.relations().newRelation(topic3.getId(), topic2.getId());
        final TopicPatch topicPatch = new TopicPatch(topic1.getId());
        topicPatch.addOldTopicId(topic3.getId());

        relationManager.merge(topicPatch);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(1));
        assertThat(relations.get(0).getWeight(), is(2.0));
    }

    private RelationManager relationManager;
}
