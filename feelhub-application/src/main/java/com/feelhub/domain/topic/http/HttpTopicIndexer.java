package com.feelhub.domain.topic.http;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.TopicIndexer;
import com.feelhub.domain.topic.http.uri.*;
import com.feelhub.repositories.Repositories;
import com.google.common.eventbus.*;

public class HttpTopicIndexer {

    public HttpTopicIndexer() {
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    @Synchronize
    public void onHttpTopicCreated(final HttpTopicCreatedEvent event) {
        final HttpTopic httpTopic = Repositories.topics().getHttpTopic(event.topicId);
        index(httpTopic, event.resolverResult);
    }

    public void index(final HttpTopic topic, final ResolverResult resolverResult) {
        final TopicIndexer topicIndexer = new TopicIndexer(topic);
        for (final Uri uri : resolverResult.getPath()) {
            for (final String variation : uri.getVariations()) {
                topicIndexer.index(variation, FeelhubLanguage.none());
            }
        }
    }
}
