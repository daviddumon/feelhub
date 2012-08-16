package com.steambeat.domain.keyword;

import com.steambeat.domain.eventbus.WithDomainEvent;
import com.steambeat.domain.thesaurus.Language;
import com.steambeat.domain.topic.Topic;
import com.steambeat.test.*;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsKeywordFactory {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        keywordFactory = new KeywordFactory();
    }

    @Test
    public void canCreateAKeyword() {
        final String value = "value";
        final Language language = Language.forString("english");
        final Topic topic = TestFactories.topics().newTopic();

        Keyword keyword = keywordFactory.createKeyword(value, language, topic.getId());

        assertNotNull(keyword);
        assertThat(keyword.getValue(), is(value));
        assertThat(keyword.getLanguage(), is(language));
        assertThat(keyword.getId(), notNullValue());
        assertThat(keyword.getTopicId(), notNullValue());
        assertThat(keyword.getCreationDate(), is(time.getNow()));
        assertThat(keyword.getLastModificationDate(), is(time.getNow()));
    }

    @Test
    public void postEventOnKeywordCreation() {
        bus.capture(KeywordCreatedEvent.class);
        final String value = "value";
        final Language language = Language.forString("english");
        final Topic topic = TestFactories.topics().newTopic();

        Keyword keyword = keywordFactory.createKeyword(value, language, topic.getId());

        final KeywordCreatedEvent keywordCreatedEvent = bus.lastEvent(KeywordCreatedEvent.class);
        assertThat(keywordCreatedEvent, notNullValue());
        assertThat(keywordCreatedEvent.getKeyword(), is(keyword));
        assertThat(keywordCreatedEvent.getDate(), is(time.getNow()));
    }

    @Test
    public void canCreateKeywordsWithSameTopic() {
        final Topic topic = TestFactories.topics().newTopic();

        Keyword keyword1 = keywordFactory.createKeyword("value1", Language.reference(), topic.getId());
        Keyword keyword2 = keywordFactory.createKeyword("value2", Language.reference(), topic.getId());

        assertThat(keyword1.getTopicId(), is(keyword2.getTopicId()));
    }

    private KeywordFactory keywordFactory;
}
