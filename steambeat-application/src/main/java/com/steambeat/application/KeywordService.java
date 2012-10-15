package com.steambeat.application;

import com.google.inject.Inject;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.domain.translation.*;
import com.steambeat.domain.uri.*;
import com.steambeat.repositories.Repositories;

import java.util.*;
import java.util.regex.Pattern;

public class KeywordService {

    @Inject
    public KeywordService(final ReferenceService referenceService, final KeywordFactory keywordFactory, final Translator translator, final UriManager uriManager) {
        this.referenceService = referenceService;
        this.keywordFactory = keywordFactory;
        this.translator = translator;
        this.uriManager = uriManager;
    }

    public Keyword lookUp(final String value, final SteambeatLanguage steambeatLanguage) {
        final Keyword keyword = Repositories.keywords().forValueAndLanguage(value, steambeatLanguage);
        if (keyword == null) {
            throw new KeywordNotFound();
        }
        return keyword;
    }

    public Keyword lookUp(final UUID referenceId, final SteambeatLanguage language) {
        Keyword keyword;
        final List<Keyword> keywords = Repositories.keywords().forReferenceId(referenceId);
        if (!keywords.isEmpty()) {
            keyword = getGoodKeyword(keywords, language);
        } else {
            keyword = new Keyword("?", language, referenceId);
        }
        return keyword;
    }

    private Keyword getGoodKeyword(final List<Keyword> keywords, final SteambeatLanguage steambeatLanguage) {
        Keyword referenceKeyword = null;
        for (Keyword keyword : keywords) {
            if (keyword.getLanguage().equals(steambeatLanguage)) {
                return keyword;
            } else if (keyword.getLanguage().equals(SteambeatLanguage.reference())) {
                referenceKeyword = keyword;
            }
        }
        if (referenceKeyword != null) {
            return referenceKeyword;
        } else {
            return keywords.get(0);
        }
    }

    public Keyword createKeyword(final String value, final SteambeatLanguage steambeatLanguage) {
        if (KeywordService.isUri(value)) {
            return createUri(value);
        } else {
            return createConcept(value, steambeatLanguage);
        }
    }

    private Keyword createUri(final String value) {
        try {
            final List<String> tokens = uriManager.getTokens(value);
            Reference reference;
            for (String token : tokens) {
                final Keyword keyword = lookUp(value, SteambeatLanguage.none());
            }
            return null;
            // APPELER LE SERVICE UQI MIGRE LES EREFERENCES A LA FIN
        } catch (UriException e) {
            e.printStackTrace();
            return null;
        }
        //DomainEventBus.INSTANCE.post(uriIllustrationRequestEvent);
    }

    private Keyword createConcept(final String value, final SteambeatLanguage steambeatLanguage) {
        if (!steambeatLanguage.equals(SteambeatLanguage.reference()) && !steambeatLanguage.equals(SteambeatLanguage.none())) {
            try {
                final String translatedValue = translator.translateToEnglish(value, steambeatLanguage);
                try {
                    final Keyword referenceKeyword = lookUp(translatedValue, SteambeatLanguage.reference());
                    return createKeyword(value, steambeatLanguage, referenceKeyword.getReferenceId());
                } catch (KeywordNotFound e) {
                    final Reference reference = referenceService.newReference();
                    createKeyword(translatedValue, SteambeatLanguage.reference(), reference.getId());
                    return createKeyword(value, steambeatLanguage, reference.getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
                final Reference reference = referenceService.newReference();
                final Keyword keyword = createKeyword(value, steambeatLanguage, reference.getId());
                keyword.setTranslationNeeded(true);
                return keyword;
            }
        } else {
            return createKeyword(value, steambeatLanguage, referenceService.newReference().getId());
        }
        //DomainEventBus.INSTANCE.post(conceptIllustrationRequestEvent);
    }

    public Keyword createKeyword(final String value, final SteambeatLanguage steambeatLanguage, final UUID referenceID) {
        final Keyword keyword = keywordFactory.createKeyword(value, steambeatLanguage, referenceID);
        Repositories.keywords().add(keyword);
        return keyword;
    }

    public static boolean isUri(final String text) {
        return URI_PATTERN.matcher(text).matches();
    }

    public Keyword lookUpOrCreate(final String value, final String languageCode, final OpinionService opinionService) {
        Keyword keyword;
        try {
            keyword = lookUp(value, SteambeatLanguage.forString(languageCode));
        } catch (KeywordNotFound e) {
            keyword = createKeyword(value, SteambeatLanguage.forString(languageCode));
        }
        return keyword;
    }

    private ReferenceService referenceService;
    private final KeywordFactory keywordFactory;
    private Translator translator;
    private UriManager uriManager;
    private static final Pattern URI_PATTERN = Pattern.compile("((http|https)://)?([%a-zA-Z_0-9\\.-]+)(\\.[a-z]{2,3}){1}(/.*$)?", Pattern.CASE_INSENSITIVE);
}
