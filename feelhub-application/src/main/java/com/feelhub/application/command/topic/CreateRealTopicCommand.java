package com.feelhub.application.command.topic;

import com.feelhub.application.command.Command;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.TopicFactory;
import com.feelhub.domain.topic.real.*;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class CreateRealTopicCommand implements Command<UUID> {

    public CreateRealTopicCommand(final FeelhubLanguage feelhubLanguage, final String name, final RealTopicType type, final UUID userID) {
        this.feelhubLanguage = feelhubLanguage;
        this.name = name;
        this.type = type;
        this.userID = userID;
    }

    @Override
    public UUID execute() {
        final RealTopic realTopic = new TopicFactory().createRealTopic(feelhubLanguage, name, type, userID);
        Repositories.topics().add(realTopic);
        return realTopic.getId();
    }


    public final FeelhubLanguage feelhubLanguage;
    public final String name;
    public final RealTopicType type;
    public final UUID userID;
}
