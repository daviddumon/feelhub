package com.feelhub.application.command.topic;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.http.HttpTopicThumbnailUpdateRequestedEvent;
import com.feelhub.domain.topic.real.RealTopicThumbnailUpdateRequestedEvent;
import com.feelhub.domain.topic.world.WorldTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.Rule;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class UpdateThumbnailTopicCommandTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent events = new WithDomainEvent();

    @Test
    public void canEmitAnHttpTopicThumbnailUpdateNeededEvent() {
        Topic topic = TestFactories.topics().newCompleteHttpTopic();

        new UpdateThumbnailTopicCommand(topic.getId()).execute();

        HttpTopicThumbnailUpdateRequestedEvent event = events.lastEvent(HttpTopicThumbnailUpdateRequestedEvent.class);
        assertThat(event).isNotNull();
        assertThat(event.topicId).isEqualTo(topic.getId());
        assertThat(event.feelhubLanguage).isEqualTo(FeelhubLanguage.fromCode(topic.getLanguageCode()));
    }

    @Test
    public void canEmitAnRealTopicThumbnailUpdateNeededEvent() {
        Topic topic = TestFactories.topics().newCompleteRealTopic();

        new UpdateThumbnailTopicCommand(topic.getId()).execute();

        RealTopicThumbnailUpdateRequestedEvent event = events.lastEvent(RealTopicThumbnailUpdateRequestedEvent.class);
        assertThat(event).isNotNull();
        assertThat(event.topicId).isEqualTo(topic.getId());
        assertThat(event.feelhubLanguage).isEqualTo(FeelhubLanguage.fromCode(topic.getLanguageCode()));
    }

    @Test
    public void voidDoNothingOnWorldTopic() {
        Topic topic = TestFactories.topics().newWorldTopic();

        new UpdateThumbnailTopicCommand(topic.getId()).execute();

        RealTopicThumbnailUpdateRequestedEvent event = events.lastEvent(RealTopicThumbnailUpdateRequestedEvent.class);
        assertThat(event).isNull();
    }
}
