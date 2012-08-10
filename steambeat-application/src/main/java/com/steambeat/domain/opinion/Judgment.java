package com.steambeat.domain.opinion;

import com.steambeat.domain.topic.Topic;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class Judgment {

    // Constructor for mapper : do not delete !
    protected Judgment() {
    }

    public Judgment(final Topic topic, final Feeling feeling) {
        this.topicId = topic.getId();
        this.feeling = feeling;
    }

    public Feeling getFeeling() {
        return feeling;
    }

    public Topic getTopic() {
        return Repositories.topics().get(topicId);
    }

    public UUID getTopicId() {
        return topicId;
    }

    private UUID topicId;
    private Feeling feeling;
}
