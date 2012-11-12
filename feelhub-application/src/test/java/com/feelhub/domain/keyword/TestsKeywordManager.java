package com.feelhub.domain.keyword;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.TopicPatch;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsKeywordManager {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        keywordManager = new KeywordManager();
    }

    @Test
    public void canTopicForAKeyword() {
        final Keyword first = TestFactories.keywords().newWord();
        final Keyword second = TestFactories.keywords().newWord();
        final TopicPatch topicPatch = new TopicPatch(first.getTopicId());
        topicPatch.addOldTopicId(second.getTopicId());

        keywordManager.merge(topicPatch);

        assertThat(second.getTopicId()).isEqualTo(first.getTopicId());
    }

    private KeywordManager keywordManager;
}
