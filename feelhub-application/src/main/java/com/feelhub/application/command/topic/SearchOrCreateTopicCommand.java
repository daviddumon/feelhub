package com.feelhub.application.command.topic;

import com.feelhub.application.command.Command;
import com.feelhub.application.search.TopicSearch;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.TopicFactory;
import com.feelhub.domain.topic.TopicIdentifier;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.uri.UriResolver;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.topic.real.RealTopicType;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;

import java.util.List;
import java.util.UUID;

public class SearchOrCreateTopicCommand implements Command<UUID> {

    public SearchOrCreateTopicCommand(String value, UUID userId) {
        this.value = value;
        this.userId = userId;
    }

    @Override
    public UUID execute() {
        List<Topic> topics = search.findTopics(value, user().getLanguage());
        if(topics.isEmpty()) {
            return createTopic();
        }
        return topics.get(0).getId();
    }

    private UUID createTopic() {
        if (TopicIdentifier.isHttpTopic(value)) {

            final HttpTopic httpTopic = new TopicFactory().createHttpTopic(value, userId, resolver);
            Repositories.topics().add(httpTopic);
            return httpTopic.getId();
        } else {
            final RealTopic realTopic = new TopicFactory().createRealTopic(user().getLanguage(), value, RealTopicType.Other, userId);
            Repositories.topics().add(realTopic);
            return realTopic.getId();
        }
    }

    private User user() {
        return Repositories.users().get(userId);
    }

    public final String value;
    public final UUID userId;
    UriResolver resolver = new UriResolver();
    TopicSearch search = new TopicSearch();
}
