package com.feelhub.domain.topic.http;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.TopicThumbnailUpdateRequestedEvent;

import java.util.UUID;

public class HttpTopicThumbnailUpdateRequestedEvent extends TopicThumbnailUpdateRequestedEvent {
    public HttpTopicThumbnailUpdateRequestedEvent(UUID topicId, FeelhubLanguage feelhubLanguage) {
        super(topicId, feelhubLanguage);
    }

}
