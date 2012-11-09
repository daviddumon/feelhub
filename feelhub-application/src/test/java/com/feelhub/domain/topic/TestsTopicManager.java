package com.feelhub.domain.topic;

import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsTopicManager {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        topicManager = new TopicManager();
    }

    @Test
    public void setOnlyTheOldestTopicAsActive() {
        final Keyword goodKeyword = TestFactories.keywords().newKeyword("fr", FeelhubLanguage.forString("fr"));
        time.waitDays(1);
        final Keyword badKeyword = TestFactories.keywords().newKeyword("en", FeelhubLanguage.forString("en"));
        final TopicPatch topicPatch = new TopicPatch(goodKeyword.getTopicId());
        topicPatch.addOldTopicId(badKeyword.getTopicId());
        final UUID oldId = badKeyword.getTopicId();
        final UUID goodId = goodKeyword.getTopicId();

        topicManager.merge(topicPatch);

        final Topic badTopic = Repositories.topics().get(oldId);
        assertThat(badTopic, notNullValue());
        assertThat(badTopic.isActive(), is(false));
        final Topic goodTopic = Repositories.topics().get(goodId);
        assertThat(goodTopic, notNullValue());
        assertThat(goodTopic.isActive(), is(true));
    }

    @Test
    public void oldTopicKeepALinkToTheNewTopic() {
        final Keyword goodKeyword = TestFactories.keywords().newKeyword("fr", FeelhubLanguage.forString("fr"));
        time.waitDays(1);
        final Keyword badKeyword = TestFactories.keywords().newKeyword("en", FeelhubLanguage.forString("en"));
        final UUID oldId = badKeyword.getTopicId();
        final UUID goodId = goodKeyword.getTopicId();
        final TopicPatch topicPatch = new TopicPatch(goodId);
        topicPatch.addOldTopicId(oldId);

        topicManager.merge(topicPatch);

        final Topic badTopic = Repositories.topics().get(oldId);
        assertThat(badTopic.isActive(), is(false));
        assertThat(badTopic.getCurrentTopicId(), is(goodId));
    }

    private TopicManager topicManager;
}
