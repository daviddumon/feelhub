package com.steambeat.domain.concept;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.SessionProvider;

public abstract class Translator {

    public Translator(final SessionProvider sessionProvider, final KeywordService keywordService) {
        this.sessionProvider = sessionProvider;
        this.keywordService = keywordService;
        Translate.setClientId("steambeat");
        Translate.setClientSecret("XrLNktvCKuAOe12+TUOLWwJuZiju+5iCwvMRCLGpB8Q=");
    }

    protected String translateKeywordToEnglish(final Keyword keyword) throws Exception {
        return Translate.execute(keyword.getValue(), keyword.getLanguage().getMicrosoftLanguage(), Language.ENGLISH);
    }

    protected void addKeywordFor(final String result, final ConceptEvent event) {
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

    protected KeywordService keywordService;
    protected SessionProvider sessionProvider;
}
