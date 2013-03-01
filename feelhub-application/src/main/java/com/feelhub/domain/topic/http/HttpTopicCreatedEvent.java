package com.feelhub.domain.topic.http;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.topic.http.uri.ResolverResult;
import com.google.common.base.Objects;

import java.util.UUID;

public class HttpTopicCreatedEvent extends DomainEvent {

    public HttpTopicCreatedEvent(final UUID topicId, final ResolverResult resolverResult) {
        this.topicId = topicId;
        this.resolverResult = resolverResult;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Topic Id", topicId).toString();
    }

    public final UUID topicId;
    public final ResolverResult resolverResult;
}
