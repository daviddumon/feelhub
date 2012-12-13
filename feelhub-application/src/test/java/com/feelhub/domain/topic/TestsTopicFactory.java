package com.feelhub.domain.topic;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.topic.web.*;
import com.feelhub.domain.topic.world.WorldTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.SystemTime;
import com.google.inject.*;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsTopicFactory {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        topicFactory = injector.getInstance(TopicFactory.class);
    }

    @Test
    public void canCreateRealTopic() {
        final RealTopic realTopic = topicFactory.createRealTopic(FeelhubLanguage.REFERENCE, "topic", RealTopicType.City);

        assertThat(realTopic).isNotNull();
        assertThat(realTopic.getCurrentId()).isEqualTo(realTopic.getId());
        assertThat(realTopic.getName(FeelhubLanguage.REFERENCE)).isEqualTo("Topic");
        assertThat(realTopic.getType()).isEqualTo(RealTopicType.City);
    }

    @Test
    public void canCreateWebTopic() {
        final WebTopic webTopic = topicFactory.createWebTopic("http://www.url.com", WebTopicType.Article);

        assertThat(webTopic).isNotNull();
        assertThat(webTopic.getType()).isEqualTo(WebTopicType.Article);
        assertThat(webTopic.getName(FeelhubLanguage.none())).isEqualTo("Http://www.url.com");
    }

    @Test
    public void canCreateWorldTopic() {
        final WorldTopic worldTopic = topicFactory.createWorldTopic();

        assertThat(worldTopic).isNotNull();
    }

    private TopicFactory topicFactory;
}