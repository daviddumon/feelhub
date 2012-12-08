package com.feelhub.domain.translation;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.usable.real.RealTopic;
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
        try {
            final String referenceDescription = translateToReference(name, feelhubLanguage);
            realTopic.addName(FeelhubLanguage.REFERENCE, referenceDescription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String translateToReference(final String value, final FeelhubLanguage feelhubLanguage) throws Exception {
        return Translate.execute(value, feelhubLanguage.getMicrosoftLanguage(), FeelhubLanguage.REFERENCE.getMicrosoftLanguage());
    }
}
