package com.feelhub.web.search;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.illustration.Illustration;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.TestWithMongoRepository;
import com.feelhub.test.*;
import com.google.common.collect.Lists;
import org.junit.*;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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
        final Topic topic = TestFactories.topics().newTopic();
        final String link = "http://www.illustration.com/1.jpg";
        final Illustration illustration = TestFactories.illustrations().newIllustration(topic, link);
        final List<UUID> topics = Lists.newArrayList();
        topics.add(topic.getId());

        final List<Illustration> illustrations = illustrationSearch.withTopics(topics).execute();

        assertThat(illustrations, notNullValue());
        assertThat(illustrations.size(), is(1));
        assertThat(illustrations.get(0), is(illustration));
    }

    @Test
    public void canGetOnlySomeIllustrations() {
        final Topic topic1 = TestFactories.topics().newTopic();
        final Topic topic2 = TestFactories.topics().newTopic();
        final Topic topic3 = TestFactories.topics().newTopic();
        final String link = "http://www.illustration.com/1.jpg";
        TestFactories.illustrations().newIllustration(topic1, link);
        TestFactories.illustrations().newIllustration(topic2, link);
        TestFactories.illustrations().newIllustration(topic3, link);
        final List<UUID> topics = Lists.newArrayList();
        topics.add(topic1.getId());
        topics.add(topic2.getId());

        final List<Illustration> illustrations = illustrationSearch.withTopics(topics).execute();

        assertThat(illustrations.size(), is(2));
    }

    private IllustrationSearch illustrationSearch;
}
