package com.feelhub.domain.topic.real;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.TopicThumbnailUpdateRequestedEvent;

import java.util.UUID;

public class RealTopicThumbnailUpdateRequestedEvent extends TopicThumbnailUpdateRequestedEvent {
    public RealTopicThumbnailUpdateRequestedEvent(UUID topicId, FeelhubLanguage feelhubLanguage) {
        super(topicId, feelhubLanguage);
    }

}
