package com.feelhub.domain.translation;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.google.common.eventbus.Subscribe;
import com.memetix.mst.translate.Translate;

import java.util.*;

public class Translator {

    public Translator() {
        Translate.setClientId("steambeat");
        Translate.setClientSecret("XrLNktvCKuAOe12+TUOLWwJuZiju+5iCwvMRCLGpB8Q=");
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void onTranslationRequest(final ReferenceTranslatioRequestEvent referenceTranslatioRequestEvent) {
        final Topic topic = referenceTranslatioRequestEvent.getTopic();
        final Iterator<Map.Entry<String, String>> iterator = topic.getDescriptions().entrySet().iterator();
        if (iterator.hasNext()) {
            final Map.Entry<String, String> description = iterator.next();
            try {
                final String referenceDescription = translateToReference(description.getValue(), FeelhubLanguage.fromCode(description.getKey()));
                topic.addDescription(FeelhubLanguage.REFERENCE, referenceDescription);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String translateToReference(final String value, final FeelhubLanguage feelhubLanguage) throws Exception {
        return Translate.execute(value, feelhubLanguage.getMicrosoftLanguage(), FeelhubLanguage.REFERENCE.getMicrosoftLanguage());
    }
}
