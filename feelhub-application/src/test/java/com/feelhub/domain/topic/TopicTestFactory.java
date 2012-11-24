package com.feelhub.domain.topic;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class TopicTestFactory {

    public Topic newTopic() {
        final Topic topic = new Topic(UUID.randomUUID());
        topic.setType(TopicType.Website);
        topic.addDescription(FeelhubLanguage.reference(), "description-reference");
        topic.addDescription(FeelhubLanguage.fromCode("fr"), "description-fr");
        topic.addSubType("subtype1");
        topic.addSubType("subtype2");
        topic.addUrl("http://www.fakeurl.com");
        Repositories.topics().add(topic);
        return topic;
    }

    public Topic newTopicWithTypeNone() {
        final Topic topic = new Topic(UUID.randomUUID());
        topic.addDescription(FeelhubLanguage.reference(), "description-reference");
        topic.addDescription(FeelhubLanguage.fromCode("fr"), "description-fr");
        topic.addUrl("http://www.fakeurl.com");
        Repositories.topics().add(topic);
        return topic;
    }

    public Topic newWorld() {
        final Topic topic = new Topic(UUID.randomUUID());
        topic.setType(TopicType.World);
        Repositories.topics().add(topic);
        return topic;
    }
}
