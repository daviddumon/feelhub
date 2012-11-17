package com.feelhub.domain.keyword;

import com.feelhub.domain.keyword.uri.Uri;
import com.feelhub.domain.keyword.word.Word;
import com.feelhub.domain.keyword.world.World;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.google.inject.*;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsKeywordFactory {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        keywordFactory = injector.getInstance(KeywordFactory.class);
    }

    @Test
    public void canCreateAWord() {
        final String value = "value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.fromCountryName("english");
        final Topic topic = TestFactories.topics().newTopic();

        final Word word = keywordFactory.createWord(value, feelhubLanguage, topic.getId());

        assertThat(word).isNotNull();
        assertThat(word.getValue()).isEqualTo(value);
        assertThat(word.getLanguage()).isEqualTo(feelhubLanguage);
        assertThat(word.getId()).isNotNull();
        assertThat(word.getTopicId()).isNotNull();
        assertThat(word.getCreationDate()).isEqualTo(time.getNow());
        assertThat(word.getLastModificationDate()).isEqualTo(time.getNow());
        assertThat(word.isTranslationNeeded()).isFalse();
    }

    @Test
    public void canCreateKeywordsWithSameTopic() {
        final Topic topic = TestFactories.topics().newTopic();

        final Word word1 = keywordFactory.createWord("value1", FeelhubLanguage.reference(), topic.getId());
        final Word word2 = keywordFactory.createWord("value2", FeelhubLanguage.reference(), topic.getId());

        assertThat(word1.getTopicId()).isEqualTo(word2.getTopicId());
    }

    @Test
    public void canCreateAnUri() {
        final String value = "http://www.google.fr";
        final Topic topic = TestFactories.topics().newTopic();

        final Uri uri = keywordFactory.createUri(value, topic.getId());

        assertThat(uri).isNotNull();
        assertThat(uri.getValue()).isEqualTo(value);
        assertThat(uri.getLanguage()).isEqualTo(FeelhubLanguage.none());
        assertThat(uri.getId()).isNotNull();
        assertThat(uri.getTopicId()).isNotNull();
        assertThat(uri.getCreationDate()).isEqualTo(time.getNow());
        assertThat(uri.getLastModificationDate()).isEqualTo(time.getNow());
    }

    @Test
    public void canSetTranslationNeededForAWord() {
        final Word word = keywordFactory.createWord("value", FeelhubLanguage.fromCountryName("english"), TestFactories.topics().newTopic().getId());

        word.setTranslationNeeded(true);

        assertThat(word.isTranslationNeeded()).isTrue();
    }

    @Test
    public void canCreateWorld() {
        final Topic topic = TestFactories.topics().newTopic();

        final World world = keywordFactory.createWorld(topic.getId());

        assertThat(world).isNotNull();
        assertThat(world.getValue()).isEqualTo("");
        assertThat(world.getLanguage()).isEqualTo(FeelhubLanguage.none());
        assertThat(world.getId()).isNotNull();
        assertThat(world.getTopicId()).isEqualTo(topic.getId());
        assertThat(world.getCreationDate()).isEqualTo(time.getNow());
        assertThat(world.getLastModificationDate()).isEqualTo(time.getNow());
    }

    private KeywordFactory keywordFactory;
}
