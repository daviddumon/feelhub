package com.feelhub.domain.translation;

import com.feelhub.domain.admin.ApiCallEvent;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.TopicIndexer;
import com.feelhub.domain.topic.real.RealTopic;
import com.google.common.eventbus.Subscribe;
import com.memetix.mst.translate.Translate;

public class Translator {

    public Translator() {
        Translate.setClientId("steambeat");
        Translate.setClientSecret("XrLNktvCKuAOe12+TUOLWwJuZiju+5iCwvMRCLGpB8Q=");
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void onTranslationRequest(final ReferenceTranslationRequestEvent referenceTranslationRequestEvent) {
        final RealTopic realTopic = referenceTranslationRequestEvent.getRealTopic();
        final FeelhubLanguage feelhubLanguage = referenceTranslationRequestEvent.getFeelhubLanguage();
        final String name = referenceTranslationRequestEvent.getName();
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
