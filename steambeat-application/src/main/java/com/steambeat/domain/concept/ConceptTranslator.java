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
    public void translate(final ConceptEvent event) {
        sessionProvider.start();
        for (Keyword keyword : event.getKeywords()) {
            final SteambeatLanguage steambeatLanguage = keyword.getLanguage();
            if (!steambeatLanguage.equals(SteambeatLanguage.reference()) && !steambeatLanguage.equals(SteambeatLanguage.none())) {
                try {
                    addKeywordFor(translateKeywordToEnglish(keyword), event);
                    postTranslationDoneEvent(event);
                } catch (Exception e) {
                }
            }
        }
        sessionProvider.stop();
    }

    protected String translateKeywordToEnglish(final Keyword keyword) throws Exception {
        return Translate.execute(keyword.getValue(), keyword.getLanguage().getMicrosoftLanguage(), Language.ENGLISH);
    }

    private void addKeywordFor(final String result, final ConceptEvent event) {
        Keyword keyword = getOrCreateKeyword(result);
        event.addIfAbsent(keyword);
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

    private void postTranslationDoneEvent(final ConceptEvent event) {
        final ConceptTranslatedEvent conceptTranslatedEvent = new ConceptTranslatedEvent();
        conceptTranslatedEvent.addAllAbsent(event.getKeywords());
        DomainEventBus.INSTANCE.post(conceptTranslatedEvent);
    }

    private KeywordService keywordService;
    private SessionProvider sessionProvider;
}
