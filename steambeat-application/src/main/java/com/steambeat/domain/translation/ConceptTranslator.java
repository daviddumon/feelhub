package com.steambeat.domain.translation;

import com.google.common.eventbus.*;
import com.google.inject.Inject;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.subject.concept.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;

public class ConceptTranslator {

    @Inject
    public ConceptTranslator(final KeywordService keywordService) {
        this.keywordService = keywordService;
        Translate.setClientId("steambeat");
        Translate.setClientSecret("XrLNktvCKuAOe12+TUOLWwJuZiju+5iCwvMRCLGpB8Q=");
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void translate(final ConceptCreatedEvent event) {
        translate(event.getConcept());
        postTranslationDoneEvent(event.getConcept());
    }

    private void translate(final Concept concept) {
        for (Keyword keyword : concept.getKeywords()) {
            final SteambeatLanguage steambeatLanguage = keyword.getLanguage();
            if (!steambeatLanguage.equals(SteambeatLanguage.reference()) && !steambeatLanguage.equals(SteambeatLanguage.none())) {
                try {
                    addKeywordFor(translateKeywordToEnglish(keyword), concept);
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
            keyword = keywordService.createKeyword(result, SteambeatLanguage.reference());
        }
        return keyword;
    }

    private void postTranslationDoneEvent(final Concept concept) {
        final TranslationDoneEvent translationDoneEvent = new TranslationDoneEvent(concept);
        DomainEventBus.INSTANCE.post(translationDoneEvent);
    }

    private KeywordService keywordService;
}
