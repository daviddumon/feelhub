package com.feelhub.domain.translation;

import com.feelhub.domain.admin.ApiCallEvent;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.TopicIndexer;
import com.feelhub.domain.topic.real.*;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Iterables;
import com.google.common.eventbus.Subscribe;
import com.memetix.mst.translate.Translate;

import java.util.Map;

public class Translator {

    public Translator() {
        Translate.setClientId("steambeat");
        Translate.setClientSecret("XrLNktvCKuAOe12+TUOLWwJuZiju+5iCwvMRCLGpB8Q=");
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void onRealTopicCreated(final RealTopicCreatedEvent event) {
        final RealTopic realTopic = Repositories.topics().getRealTopic(event.topicId);
        translateReference(realTopic);
    }

    void translateReference(RealTopic realTopic) {
        if (!realTopic.mustTranslate()) {
            return;
        }
        Map.Entry<String, String> nameAndLanguage = Iterables.get(realTopic.getNames().entrySet(), 0);
        final FeelhubLanguage feelhubLanguage = FeelhubLanguage.fromCode(nameAndLanguage.getKey());
        final String name = nameAndLanguage.getValue();
        final String referenceDescription = translateToReference(name, feelhubLanguage);
        DomainEventBus.INSTANCE.post(ApiCallEvent.microsoftTranslate(name.length()));
        realTopic.addName(FeelhubLanguage.reference(), referenceDescription);
        new TopicIndexer(realTopic).index(referenceDescription, FeelhubLanguage.reference());
    }

    protected String translateToReference(final String value, final FeelhubLanguage feelhubLanguage) {
        try {
            return Translate.execute(value, feelhubLanguage.getMicrosoftLanguage(), FeelhubLanguage.REFERENCE.getMicrosoftLanguage());
        } catch (Exception e) {
            return "";
        }
    }

}
