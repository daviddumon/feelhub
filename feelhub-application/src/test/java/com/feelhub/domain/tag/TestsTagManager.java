package com.feelhub.domain.tag;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.TopicPatch;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsTagManager {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        tagManager = injector.getInstance(TagManager.class);
    }

    @Test
    public void canMerge() {
        final Tag first = TestFactories.tags().newTag();
        final RealTopic oldRealTopic = TestFactories.topics().newCompleteRealTopic();
        first.addTopic(oldRealTopic);
        first.addTopic(TestFactories.topics().newCompleteRealTopic());
        final Tag second = TestFactories.tags().newTag();
        second.addTopic(TestFactories.topics().newCompleteRealTopic());
        second.addTopic(TestFactories.topics().newCompleteRealTopic());
        final RealTopic newRealTopic = TestFactories.topics().newCompleteRealTopic();
        final TopicPatch topicPatch = new TopicPatch(newRealTopic.getId());
        topicPatch.addOldTopicId(oldRealTopic.getId());

        tagManager.merge(topicPatch);

        assertThat(first.getTopicIds()).contains(newRealTopic.getId());
    }

    private TagManager tagManager;
}
