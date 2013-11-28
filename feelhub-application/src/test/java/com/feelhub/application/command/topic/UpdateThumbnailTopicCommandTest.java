package com.feelhub.application.command.topic;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.http.HttpTopicThumbnailUpdateNeededEvent;
import com.feelhub.domain.topic.http.RealTopicThumbnailUpdateNeededEvent;
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

        HttpTopicThumbnailUpdateNeededEvent event = events.lastEvent(HttpTopicThumbnailUpdateNeededEvent.class);
        assertThat(event).isNotNull();
        assertThat(event.topicId).isEqualTo(topic.getId());
    }

    @Test
    public void canEmitAnRealTopicThumbnailUpdateNeededEvent() {
        Topic topic = TestFactories.topics().newCompleteRealTopic();

        new UpdateThumbnailTopicCommand(topic.getId()).execute();

        RealTopicThumbnailUpdateNeededEvent event = events.lastEvent(RealTopicThumbnailUpdateNeededEvent.class);
        assertThat(event).isNotNull();
        assertThat(event.topicId).isEqualTo(topic.getId());
    }
}
