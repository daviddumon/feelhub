package com.feelhub.domain.keyword;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import org.junit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsKeywordFactory {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        keywordFactory = new KeywordFactory();
    }

    @Test
    public void canCreateAKeyword() {
        final String value = "value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.forString("english");
        final Topic topic = TestFactories.topics().newTopic();

        final Keyword keyword = keywordFactory.createKeyword(value, feelhubLanguage, topic.getId());

        assertNotNull(keyword);
        assertThat(keyword.getValue(), is(value));
        assertThat(keyword.getLanguage(), is(feelhubLanguage));
        assertThat(keyword.getId(), notNullValue());
        assertThat(keyword.getTopicId(), notNullValue());
        assertThat(keyword.getCreationDate(), is(time.getNow()));
        assertThat(keyword.getLastModificationDate(), is(time.getNow()));
    }

    @Test
    public void canCreateKeywordsWithSameTopic() {
        final Topic topic = TestFactories.topics().newTopic();

        final Keyword keyword1 = keywordFactory.createKeyword("value1", FeelhubLanguage.reference(), topic.getId());
        final Keyword keyword2 = keywordFactory.createKeyword("value2", FeelhubLanguage.reference(), topic.getId());

        assertThat(keyword1.getTopicId(), is(keyword2.getTopicId()));
    }

    private KeywordFactory keywordFactory;
}
