package com.feelhub.domain.topic.real;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.TopicIndexer;
import com.feelhub.repositories.Repositories;
import com.google.common.eventbus.*;

import java.util.Map;

public class RealTopicIndexer {

    public RealTopicIndexer() {
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @Synchronize
    @AllowConcurrentEvents
    public void handle(final RealTopicCreatedEvent event) {
        index(Repositories.topics().getRealTopic(event.topicId));
    }


    public void index(final RealTopic topic) {
        final TopicIndexer topicIndexer = new TopicIndexer(topic);
        final Map<String, String> names = topic.getNames();
        for (final String language : names.keySet()) {
            topicIndexer.index(topic.getName(FeelhubLanguage.fromCode(language)), FeelhubLanguage.fromCode(language));
        }
    }
}
