package com.feelhub.application;

import com.feelhub.domain.alchemy.AlchemyRequestEvent;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.meta.UriMetaInformationRequestEvent;
import com.feelhub.domain.topic.TopicFactory;
import com.feelhub.domain.topic.usable.real.RealTopic;
import com.feelhub.repositories.Repositories;
import com.google.inject.Inject;

public class TopicFromUriService {

    @Inject
    public TopicFromUriService(final TopicFactory topicFactory) {
        this.topicFactory = topicFactory;
    }

    public RealTopic createTopicFromUri(final String value) {
        final RealTopic realTopic = createTopic();
        requestMetaInformation(realTopic, value);
        requestAlchemyAnalysis(realTopic, value);
        return realTopic;
    }

    private RealTopic createTopic() {
        final RealTopic realTopic = topicFactory.createRealTopic();
        Repositories.topics().add(realTopic);
        return realTopic;
    }

    private void requestMetaInformation(final RealTopic realTopic, final String value) {
        final UriMetaInformationRequestEvent uriMetaInformationRequestEvent = new UriMetaInformationRequestEvent(realTopic, value);
        DomainEventBus.INSTANCE.post(uriMetaInformationRequestEvent);
    }

    private void requestAlchemyAnalysis(final RealTopic realTopic, final String value) {
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(realTopic, value);
        DomainEventBus.INSTANCE.post(alchemyRequestEvent);
    }

    private final TopicFactory topicFactory;
}
