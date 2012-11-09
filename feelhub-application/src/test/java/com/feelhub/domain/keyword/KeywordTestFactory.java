package com.feelhub.domain.keyword;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class KeywordTestFactory {

    public Keyword newKeyword() {
        final String value = "Value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.forString("english");
        return newKeyword(value, feelhubLanguage);
    }

    public Keyword newKeyword(final String value) {
        return newKeyword(value, FeelhubLanguage.none());
    }

    public Keyword newKeyword(final String value, final FeelhubLanguage feelhubLanguage) {
        final Keyword keyword = new Keyword(value, feelhubLanguage, createAndPersistTopic().getId());
        Repositories.keywords().add(keyword);
        return keyword;
    }

    public Keyword newKeyword(final String value, final FeelhubLanguage feelhubLanguage, final Topic topic) {
        final Keyword keyword = new Keyword(value, feelhubLanguage, topic.getId());
        Repositories.keywords().add(keyword);
        return keyword;
    }

    private Topic createAndPersistTopic() {
        final UUID id = UUID.randomUUID();
        final Topic topic = new Topic(id);
        Repositories.topics().add(topic);
        return topic;
    }
}
