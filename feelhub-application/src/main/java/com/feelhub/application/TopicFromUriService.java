package com.feelhub.application;

import com.feelhub.domain.alchemy.AlchemyRequestEvent;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.meta.UriMetaInformationRequestEvent;
import com.feelhub.domain.topic.*;
import com.feelhub.repositories.Repositories;
import com.google.inject.Inject;

public class TopicFromUriService {

    @Inject
    public TopicFromUriService(final TopicFactory topicFactory) {
        this.topicFactory = topicFactory;
    }

    public Topic createTopicFromUri(final String value) {
        final Topic topic = createTopic();
        requestMetaInformation(topic, value);
        requestAlchemyAnalysis(topic, value);
        return topic;
    }

    private Topic createTopic() {
        final Topic topic = topicFactory.createTopic();
        Repositories.topics().add(topic);
        return topic;
    }

    private void requestMetaInformation(final Topic topic, final String value) {
        final UriMetaInformationRequestEvent uriMetaInformationRequestEvent = new UriMetaInformationRequestEvent(topic, value);
        DomainEventBus.INSTANCE.post(uriMetaInformationRequestEvent);
    }

    private void requestAlchemyAnalysis(final Topic topic, final String value) {
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(topic, value);
        DomainEventBus.INSTANCE.post(alchemyRequestEvent);
    }

    private TopicFactory topicFactory;
}
