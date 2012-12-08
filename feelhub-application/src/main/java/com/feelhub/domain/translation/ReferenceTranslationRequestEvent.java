package com.feelhub.domain.translation;

import com.feelhub.domain.eventbus.DomainEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.usable.real.RealTopic;

public class ReferenceTranslationRequestEvent extends DomainEvent {

    public ReferenceTranslationRequestEvent(final RealTopic realTopic, final FeelhubLanguage feelhubLanguage, final String name) {
        this.realTopic = realTopic;
        this.feelhubLanguage = feelhubLanguage;
        this.name = name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public RealTopic getRealTopic() {
        return realTopic;
    }

    public FeelhubLanguage getFeelhubLanguage() {
        return feelhubLanguage;
    }

    public String getName() {
        return name;
    }

    private final RealTopic realTopic;
    private final FeelhubLanguage feelhubLanguage;
    private final String name;
}
