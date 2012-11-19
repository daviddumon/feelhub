package com.feelhub.domain.tag;

import com.feelhub.domain.tag.word.Word;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;

import java.util.UUID;

public class TagTestFactory {

    public Tag newWord() {
        final String value = "Value";
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.fromCountryName("english");
        return newWord(value, feelhubLanguage);
    }

    public Tag newWord(final String value) {
        return newWord(value, FeelhubLanguage.none());
    }

    public Tag newWord(final String value, final FeelhubLanguage feelhubLanguage) {
        final Word word = new Word(value, feelhubLanguage, createAndPersistTopic().getId());
        Repositories.keywords().add(word);
        return word;
    }

    public Tag newWord(final String value, final FeelhubLanguage feelhubLanguage, final UUID topicId) {
        final Word word = new Word(value, feelhubLanguage, topicId);
        Repositories.keywords().add(word);
        return word;
    }

    private Topic createAndPersistTopic() {
        final UUID id = UUID.randomUUID();
        final Topic topic = new Topic(id);
        Repositories.topics().add(topic);
        return topic;
    }
}
