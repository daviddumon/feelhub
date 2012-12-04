package com.feelhub.domain.tag;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.*;
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
        final Topic oldTopic = TestFactories.topics().newTopic();
        first.addTopic(oldTopic);
        first.addTopic(TestFactories.topics().newTopic());
        final Tag second = TestFactories.tags().newTag();
        second.addTopic(TestFactories.topics().newTopic());
        second.addTopic(TestFactories.topics().newTopic());
        final Topic newTopic = TestFactories.topics().newTopic();
        final TopicPatch topicPatch = new TopicPatch(newTopic.getId());
        topicPatch.addOldTopicId(oldTopic.getId());

        tagManager.merge(topicPatch);

        assertThat(first.getTopicIds()).contains(newTopic.getId());
    }

    private TagManager tagManager;
}
