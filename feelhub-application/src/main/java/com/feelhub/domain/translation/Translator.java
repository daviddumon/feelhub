package com.feelhub.domain.translation;

import com.feelhub.application.TopicService;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.Repositories;
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
            //todo absolument horrible, enlever ca des qu'une solution est trouvée au probleme d'asynchronicité
            try {
                final RealTopic foundTopic = (RealTopic) Repositories.topics().get(realTopic.getId());
                foundTopic.addName(FeelhubLanguage.reference(), referenceDescription);
                topicService.index(foundTopic, referenceDescription, FeelhubLanguage.reference());
            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String translateToReference(final String value, final FeelhubLanguage feelhubLanguage) throws Exception {
        return Translate.execute(value, feelhubLanguage.getMicrosoftLanguage(), FeelhubLanguage.REFERENCE.getMicrosoftLanguage());
    }

    private final TopicService topicService;
}
