package com.feelhub.domain.topic.real;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.TopicIndexer;
import com.feelhub.repositories.Repositories;
import com.google.common.eventbus.Subscribe;

import java.util.Map;

public class RealTopicIndexer {

    public RealTopicIndexer() {
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(RealTopicCreatedEvent event) {
        index(Repositories.topics().getRealTopic(event.eventId));
    }


    public void index(RealTopic topic) {
        TopicIndexer topicIndexer = new TopicIndexer(topic);
        Map<String,String> names = topic.getNames();
        for (String language : names.keySet()) {
            topicIndexer.index(topic.getName(FeelhubLanguage.fromCode(language)), FeelhubLanguage.fromCode(language));
        }
    }
}
