package com.steambeat.domain.concept;

import com.google.common.eventbus.*;
import com.google.inject.Inject;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.SessionProvider;

public class ConceptTranslator {

    @Inject
    public ConceptTranslator(final KeywordService keywordService, final SessionProvider sessionProvider) {
        this.keywordService = keywordService;
        this.sessionProvider = sessionProvider;
        Translate.setClientId("steambeat");
        Translate.setClientSecret("XrLNktvCKuAOe12+TUOLWwJuZiju+5iCwvMRCLGpB8Q=");
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void translate(final ConceptCreatedEvent event) {
        sessionProvider.start();
        translate(event.getConcept());
        sessionProvider.stop();
    }

    private void translate(final Concept concept) {
        for (Keyword keyword : concept.getKeywords()) {
            final SteambeatLanguage steambeatLanguage = keyword.getLanguage();
            if (!steambeatLanguage.equals(SteambeatLanguage.reference()) && !steambeatLanguage.equals(SteambeatLanguage.none())) {
                try {
                    addKeywordFor(translateKeywordToEnglish(keyword), concept);
                    postTranslationDoneEvent(concept);
                } catch (Exception e) {
                }
            }
        }
    }

    protected String translateKeywordToEnglish(final Keyword keyword) throws Exception {
        return Translate.execute(keyword.getValue(), keyword.getLanguage().getMicrosoftLanguage(), Language.ENGLISH);
    }

    private void addKeywordFor(final String result, final Concept concept) {
        Keyword keyword = getOrCreateKeyword(result);
        concept.addIfAbsent(keyword);
    }

    private Keyword getOrCreateKeyword(final String result) {
        Keyword keyword;
        try {
            keyword = keywordService.lookUp(result, SteambeatLanguage.reference());
        } catch (KeywordNotFound e) {
            keyword = keywordService.createKeywordWithoutEvent(result, SteambeatLanguage.reference());
        }
        return keyword;
    }

    private void postTranslationDoneEvent(final Concept concept) {
        final ConceptTranslatedEvent conceptTranslatedEvent = new ConceptTranslatedEvent(concept);
        DomainEventBus.INSTANCE.post(conceptTranslatedEvent);
    }

    private KeywordService keywordService;
    private SessionProvider sessionProvider;
}
