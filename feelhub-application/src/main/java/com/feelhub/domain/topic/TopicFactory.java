package com.feelhub.domain.topic;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.topic.web.*;
import com.feelhub.domain.topic.world.WorldTopic;

import java.util.UUID;

public class TopicFactory {

    public RealTopic createRealTopic(final FeelhubLanguage feelhubLanguage, final String name, final RealTopicType type) {
        final UUID id = UUID.randomUUID();
        final RealTopic realTopic = new RealTopic(id, type);
        realTopic.addName(feelhubLanguage, name);
        return realTopic;
    }

    public WorldTopic createWorldTopic() {
        return new WorldTopic(UUID.randomUUID());
    }

    public WebTopic createWebTopic(final String name, final WebTopicType type) {
        final WebTopic webTopic = new WebTopic(UUID.randomUUID(), type);
        webTopic.addName(FeelhubLanguage.none(), name);
        return webTopic;
    }
}
