package com.steambeat.application;

import com.steambeat.domain.opinion.Feeling;
import com.steambeat.domain.topic.Topic;

import java.util.UUID;

public class JudgmentDTO {

    public JudgmentDTO(final Topic topic, final Feeling feeling) {
        this.topicId = topic.getId();
        this.feeling = feeling.toString();
    }

    public UUID topicId;
    public String feeling;
}
