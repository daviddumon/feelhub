package com.feelhub.application;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.usable.real.*;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.google.inject.Inject;

import java.util.UUID;

public class TopicService {

    @Inject
    public TopicService(final TopicFactory topicFactory) {
        this.topicFactory = topicFactory;
    }

    public Topic lookUp(final UUID id) {
        final Topic topic = Repositories.topics().get(id);
        if (topic == null) {
            throw new TopicNotFound();
        }
        return topic;
    }

    public RealTopic createTopic(final FeelhubLanguage feelhubLanguage, final String name, final RealTopicType realTopicType, final User user) {
        final RealTopic realTopic = topicFactory.createRealTopic(feelhubLanguage, name, realTopicType);
        realTopic.setUserId(user.getId());
        Repositories.topics().add(realTopic);
        return realTopic;
    }

    private final TopicFactory topicFactory;
}
