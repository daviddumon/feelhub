package com.feelhub.domain.scraper;

import com.feelhub.domain.eventbus.DomainEvent;

import java.util.UUID;

public class HttpTopicAnalyzeRequest extends DomainEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public void setHttpTopicId(UUID httpTopicId) {
        this.httpTopicId = httpTopicId;
    }

    public UUID getHttpTopicId() {
        return httpTopicId;
    }

    private UUID httpTopicId;
}
