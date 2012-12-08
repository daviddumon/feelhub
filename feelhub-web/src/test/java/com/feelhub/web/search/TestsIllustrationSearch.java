package com.feelhub.web.search;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.meta.Illustration;
import com.feelhub.domain.topic.usable.real.RealTopic;
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
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final String link = "http://www.illustration.com/1.jpg";
        final Illustration illustration = TestFactories.illustrations().newIllustration(realTopic.getId(), link);
        TestFactories.illustrations().newIllustration(UUID.randomUUID());

        final List<Illustration> illustrations = illustrationSearch.withTopicId(realTopic.getId()).execute();

        assertThat(illustrations).isNotNull();
        assertThat(illustrations.size()).isEqualTo(1);
        assertThat(illustrations.get(0)).isEqualTo(illustration);
    }

    @Test
    public void canGetOnlySomeIllustrations() {
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic3 = TestFactories.topics().newCompleteRealTopic();
        final String link = "http://www.illustration.com/1.jpg";
        TestFactories.illustrations().newIllustration(realTopic1.getId(), link);
        TestFactories.illustrations().newIllustration(realTopic2.getId(), link);
        TestFactories.illustrations().newIllustration(realTopic3.getId(), link);
        final List<UUID> topics = Lists.newArrayList();
        topics.add(realTopic1.getId());
        topics.add(realTopic2.getId());

        final List<Illustration> illustrations = illustrationSearch.withTopicIds(topics).execute();

        assertThat(illustrations.size()).isEqualTo(2);
    }

    private IllustrationSearch illustrationSearch;
}
