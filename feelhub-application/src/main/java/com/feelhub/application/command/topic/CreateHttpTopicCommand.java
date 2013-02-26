package com.feelhub.application.command.topic;

import com.feelhub.application.command.Command;
import com.feelhub.domain.topic.TopicFactory;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.uri.UriResolver;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class CreateHttpTopicCommand implements Command<UUID> {

    public CreateHttpTopicCommand(final String value, final UUID userId) {
        this.value = value;
        this.userId = userId;
    }

    @Override
    public UUID execute() {
        final HttpTopic httpTopic = new TopicFactory().createHttpTopic(value, userId, resolver);
        Repositories.topics().add(httpTopic);
        return httpTopic.getId();
    }

    UriResolver resolver = new UriResolver();
    public final String value;
    public final UUID userId;
}
