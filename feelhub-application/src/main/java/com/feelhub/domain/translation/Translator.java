package com.feelhub.domain.translation;

import com.feelhub.application.TopicService;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.RealTopic;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.memetix.mst.translate.Translate;

public class Translator {

    @Inject
    public Translator(final TopicService topicService) {
        this.topicService = topicService;
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
            topicService.index(realTopic, referenceDescription);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String translateToReference(final String value, final FeelhubLanguage feelhubLanguage) throws Exception {
        return Translate.execute(value, feelhubLanguage.getMicrosoftLanguage(), FeelhubLanguage.REFERENCE.getMicrosoftLanguage());
    }

    private final TopicService topicService;
}
