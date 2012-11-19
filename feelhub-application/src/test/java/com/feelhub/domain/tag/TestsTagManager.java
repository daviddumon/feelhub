package com.feelhub.domain.tag;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.TopicPatch;
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
    public void canTopicForAKeyword() {
        final Tag first = TestFactories.tags().newWord();
        final Tag second = TestFactories.tags().newWord();
        final TopicPatch topicPatch = new TopicPatch(first.getTopicId());
        topicPatch.addOldTopicId(second.getTopicId());

        tagManager.merge(topicPatch);

        assertThat(second.getTopicId()).isEqualTo(first.getTopicId());
    }

    private TagManager tagManager;
}
