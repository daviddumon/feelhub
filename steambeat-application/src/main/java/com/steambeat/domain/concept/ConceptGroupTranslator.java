package com.steambeat.domain.concept;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.SessionProvider;

import java.util.List;

public class ConceptGroupTranslator extends Translator {

    @Inject
    public ConceptGroupTranslator(final KeywordService keywordService, final SessionProvider sessionProvider) {
        super(sessionProvider, keywordService);
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void translate(final ConceptGroupEvent event) {
        sessionProvider.start();
        for (final ConceptEvent conceptEvent : event.getConceptEvents()) {
            for (final Keyword keyword : conceptEvent.getKeywords()) {
                final SteambeatLanguage steambeatLanguage = keyword.getLanguage();
                if (!steambeatLanguage.equals(SteambeatLanguage.reference()) && !steambeatLanguage.equals(SteambeatLanguage.none())) {
                    try {
                        addKeywordFor(translateKeywordToEnglish(keyword), conceptEvent);
                    } catch (Exception e) {
                    }
                }
            }
        }
        postConceptGroupTranslatedEvent(event);
        sessionProvider.stop();
    }

    private void postConceptGroupTranslatedEvent(final ConceptGroupEvent event) {
        final List<ConceptTranslatedEvent> conceptTranslatedEvents = Lists.newArrayList();
        for (final ConceptEvent conceptEvent : event.getConceptEvents()) {
            final ConceptTranslatedEvent conceptTranslatedEvent = new ConceptTranslatedEvent();
            conceptTranslatedEvent.addAllAbsent(conceptEvent.getKeywords());
            conceptTranslatedEvents.add(conceptTranslatedEvent);
        }
        final ConceptGroupTranslatedEvent conceptGroupTranslatedEvent = new ConceptGroupTranslatedEvent(event.getReferenceId());
        conceptGroupTranslatedEvent.addAllAbsent(conceptTranslatedEvents);
        DomainEventBus.INSTANCE.post(conceptGroupTranslatedEvent);
    }
}