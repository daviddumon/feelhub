package com.steambeat.domain.concept;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.SessionProvider;

public class ConceptTranslator extends Translator {

    @Inject
    public ConceptTranslator(final KeywordService keywordService, final SessionProvider sessionProvider) {
        super(sessionProvider, keywordService);
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void translate(final ConceptEvent event) {
        sessionProvider.start();
        for (final Keyword keyword : event.getKeywords()) {
            final SteambeatLanguage steambeatLanguage = keyword.getLanguage();
            if (!steambeatLanguage.equals(SteambeatLanguage.reference()) && !steambeatLanguage.equals(SteambeatLanguage.none())) {
                try {
                    addKeywordFor(translateKeywordToEnglish(keyword), event);
                } catch (Exception e) {
                }
            }
            postConceptTranslatedEvent(event);
        }
        sessionProvider.stop();
    }

    private void postConceptTranslatedEvent(final ConceptEvent event) {
        final ConceptTranslatedEvent conceptTranslatedEvent = new ConceptTranslatedEvent();
        conceptTranslatedEvent.addAllAbsent(event.getKeywords());
        DomainEventBus.INSTANCE.post(conceptTranslatedEvent);
    }
}
