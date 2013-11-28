package com.feelhub.application.command.topic;

import com.feelhub.application.command.Command;
import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.http.HttpTopicThumbnailUpdateRequestedEvent;
import com.feelhub.domain.topic.http.HttpTopicType;
import com.feelhub.domain.topic.real.RealTopicThumbnailUpdateRequestedEvent;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;

import java.util.UUID;

public class UpdateThumbnailTopicCommand implements Command<Void> {

    public UpdateThumbnailTopicCommand(UUID topicId) {
        this.topicId = topicId;
    }

    @Override
    public Void execute() {
        DomainEventBus.INSTANCE.post(getEvent(lookUpTopic()));
        return null;
    }

    private DomainEvent getEvent(Topic topic) {
        FeelhubLanguage feelhubLanguage = FeelhubLanguage.fromCode(topic.getLanguageCode());
        if (isHttpTopic(topic)) {
            return new HttpTopicThumbnailUpdateRequestedEvent(topic.getId(), feelhubLanguage);
        }
        return new RealTopicThumbnailUpdateRequestedEvent(topic.getId(), feelhubLanguage);
    }

    private boolean isHttpTopic(Topic topic) {
        return Lists.newArrayList(HttpTopicType.values()).contains(topic.getType());
    }

    private Topic lookUpTopic() {
        return Repositories.topics().get(topicId);
    }

    public UUID topicId;

}
