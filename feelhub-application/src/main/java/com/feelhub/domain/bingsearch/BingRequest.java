package com.feelhub.domain.bingsearch;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.real.RealTopic;

public class BingRequest extends DomainEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public void setTopic(final RealTopic realTopic) {
        this.topic = realTopic;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setQuery(final String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    private RealTopic topic;
    private String query;
}
