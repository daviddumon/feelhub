package com.feelhub.domain.bing;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.topic.Topic;

import java.util.UUID;

public class BingRequest extends DomainEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public void setQuery(final String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public UUID getTopicId() {
        return topicId;
    }

    public void setTopicId(final UUID topicId) {
        this.topicId = topicId;
    }

    private UUID topicId;
    private String query;

    //todo bug race condition
    private Topic topic;

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(final Topic topic) {
        this.topic = topic;
    }
}
