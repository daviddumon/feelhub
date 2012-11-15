package com.feelhub.web.search;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.illustration.Illustration;
import com.feelhub.domain.keyword.word.Word;
import com.feelhub.repositories.TestWithMongoRepository;
import com.feelhub.test.*;
import com.google.common.collect.Lists;
import org.junit.*;

import java.util.*;

import static org.fest.assertions.Assertions.*;


public class TestsIllustrationSearch extends TestWithMongoRepository {

    @Rule
    public WithDomainEvent event = new WithDomainEvent();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        illustrationSearch = new IllustrationSearch(getProvider());
    }

    @Test
    public void canGetAnIllustration() {
        final Word word = TestFactories.keywords().newWord();
        final String link = "http://www.illustration.com/1.jpg";
        final Illustration illustration = TestFactories.illustrations().newIllustration(word.getTopicId(), link);
        TestFactories.illustrations().newIllustration(UUID.randomUUID());

        final List<Illustration> illustrations = illustrationSearch.withTopicId(word.getTopicId()).execute();

        assertThat(illustrations).isNotNull();
        assertThat(illustrations.size()).isEqualTo(1);
        assertThat(illustrations.get(0)).isEqualTo(illustration);
    }

    @Test
    public void canGetOnlySomeIllustrations() {
        final Word word1 = TestFactories.keywords().newWord();
        final Word word2 = TestFactories.keywords().newWord();
        final Word word3 = TestFactories.keywords().newWord();
        final String link = "http://www.illustration.com/1.jpg";
        TestFactories.illustrations().newIllustration(word1.getTopicId(), link);
        TestFactories.illustrations().newIllustration(word2.getTopicId(), link);
        TestFactories.illustrations().newIllustration(word3.getTopicId(), link);
        final List<UUID> topics = Lists.newArrayList();
        topics.add(word1.getTopicId());
        topics.add(word2.getTopicId());

        final List<Illustration> illustrations = illustrationSearch.withTopicIds(topics).execute();

        assertThat(illustrations.size()).isEqualTo(2);
    }

    private IllustrationSearch illustrationSearch;
}
