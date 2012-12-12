package com.feelhub.domain.topic;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.topic.world.WorldTopic;

import java.util.UUID;

public class TopicFactory {

    public RealTopic createRealTopic() {
        final UUID id = UUID.randomUUID();
        final RealTopic realTopic = new RealTopic(id, RealTopicType.Automobile);
        return realTopic;
    }

    public RealTopic createRealTopic(final FeelhubLanguage feelhubLanguage, final String name, final RealTopicType realTopicType) {
        final UUID id = UUID.randomUUID();
        final RealTopic realTopic = new RealTopic(id, realTopicType);
        realTopic.addName(feelhubLanguage, name);
        return realTopic;
    }

    public WorldTopic createWorldTopic() {
        return new WorldTopic(UUID.randomUUID());
    }
}
