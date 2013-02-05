package com.feelhub.domain.scraper;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.topic.http.HttpTopic;

public class HttpTopicAnalyzeRequest extends DomainEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public HttpTopic getHttpTopic() {
        return httpTopic;
    }

    public void setHttpTopic(final HttpTopic httpTopic) {
        this.httpTopic = httpTopic;
    }

    private HttpTopic httpTopic;
}
