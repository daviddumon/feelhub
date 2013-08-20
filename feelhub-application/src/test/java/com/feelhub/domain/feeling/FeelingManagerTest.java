package com.feelhub.domain.feeling;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.TopicPatch;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class FeelingManagerTest {

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
        feelingManager = injector.getInstance(FeelingManager.class);
    }

    @Test
    public void canChangeChangeCurrentTopicsInFeelings() {
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        final Feeling feeling1 = TestFactories.feelings().newFeeling(realTopic1, "feeling 1");
        final Feeling feeling2 = TestFactories.feelings().newFeeling(realTopic2, "feeling 2");
        final TopicPatch topicPatch = new TopicPatch(realTopic1.getId());
        topicPatch.addOldTopicId(realTopic2.getId());

        feelingManager.merge(topicPatch);

        assertThat(Repositories.feelings().get(feeling1.getId()).getTopicId()).isEqualTo(realTopic1.getCurrentId());
        assertThat(Repositories.feelings().get(feeling2.getId()).getTopicId()).isEqualTo(realTopic1.getCurrentId());
    }

    private FeelingManager feelingManager;
}
