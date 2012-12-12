package com.feelhub.domain.alchemy;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.RealTopic;

import java.util.UUID;

public class AlchemyAnalysis extends BaseEntity {

    //mongolink constructor
    protected AlchemyAnalysis() {
    }

    public AlchemyAnalysis(final RealTopic realTopic, final String value) {
        this.id = UUID.randomUUID();
        this.topicId = realTopic.getId();
        this.value = value;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public UUID getTopicId() {
        return topicId;
    }

    public String getValue() {
        return value;
    }

    public void setNewTopicId(final UUID newTopicId) {
        this.topicId = newTopicId;
    }

    public void setLanguageCode(final FeelhubLanguage language) {
        this.languageCode = language.getCode();
    }

    public String getLanguageCode() {
        return languageCode;
    }

    private UUID topicId;
    private UUID id;
    private String value;
    private String languageCode;
}
