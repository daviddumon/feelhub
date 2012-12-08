package com.feelhub.domain.relation;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.TopicPatch;
import com.feelhub.domain.topic.usable.real.RealTopic;
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
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic3 = TestFactories.topics().newCompleteRealTopic();
        final Relation relation1 = TestFactories.relations().newRelation(realTopic2.getId(), realTopic3.getId());
        final Relation relation2 = TestFactories.relations().newRelation(realTopic3.getId(), realTopic2.getId());
        final TopicPatch topicPatch = new TopicPatch(realTopic1.getId());
        topicPatch.addOldTopicId(realTopic2.getId());

        relationManager.merge(topicPatch);

        assertThat(relation1.getFromId(), is(realTopic1.getId()));
        assertThat(relation1.getToId(), is(realTopic3.getId()));
        assertThat(relation2.getFromId(), is(realTopic3.getId()));
        assertThat(relation2.getToId(), is(realTopic1.getId()));
    }

    @Test
    public void canRemoveAutoRelation() {
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newRelation(realTopic1.getId(), realTopic2.getId());
        TestFactories.relations().newRelation(realTopic2.getId(), realTopic1.getId());
        final TopicPatch topicPatch = new TopicPatch(realTopic1.getId());
        topicPatch.addOldTopicId(realTopic2.getId());

        relationManager.merge(topicPatch);

        assertThat(Repositories.relations().getAll().size(), is(0));
    }

    @Test
    public void canMergeDuplicate() {
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic3 = TestFactories.topics().newCompleteRealTopic();
        TestFactories.relations().newRelation(realTopic1.getId(), realTopic2.getId());
        TestFactories.relations().newRelation(realTopic3.getId(), realTopic2.getId());
        final TopicPatch topicPatch = new TopicPatch(realTopic1.getId());
        topicPatch.addOldTopicId(realTopic3.getId());

        relationManager.merge(topicPatch);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(1));
        assertThat(relations.get(0).getWeight(), is(2.0));
    }

    private RelationManager relationManager;
}
