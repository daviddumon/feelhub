package com.steambeat.domain.keyword;

import com.steambeat.domain.thesaurus.Language;
import com.steambeat.domain.topic.Topic;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class KeywordTestFactory {

    public Keyword newKeyword() {
        final String value = "value";
        final Language language = Language.forString("english");
        return newKeyword(value, language);
    }

    public Keyword newKeyword(final String value, final Language language) {
        final Keyword keyword = new Keyword(value, language, createAndPersistTopic().getId());
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
