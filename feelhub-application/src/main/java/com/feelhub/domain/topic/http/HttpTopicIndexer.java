package com.feelhub.domain.topic.http;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.TopicIndexer;
import com.feelhub.domain.topic.http.uri.ResolverResult;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.repositories.Repositories;
import com.google.common.eventbus.Subscribe;

public class HttpTopicIndexer {


    public HttpTopicIndexer() {
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void onHttpTopicCreated(HttpTopicCreatedEvent event) {
        HttpTopic httpTopic = Repositories.topics().getHttpTopic(event.topicId);
        index(httpTopic, event.resolverResult);
    }

    public void index(HttpTopic topic, ResolverResult resolverResult) {
        TopicIndexer topicIndexer = new TopicIndexer(topic);
        for (final Uri uri : resolverResult.getPath()) {
            for (final String variation : uri.getVariations()) {
                topicIndexer.index(variation, FeelhubLanguage.none());
            }
        }
    }
}
