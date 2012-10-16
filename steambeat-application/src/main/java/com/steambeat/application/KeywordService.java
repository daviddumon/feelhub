package com.steambeat.application;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.illustration.ConceptIllustrationRequestEvent;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.domain.translation.Translator;
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
        final Keyword keyword;
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
        for (final Keyword keyword : keywords) {
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
            final Keyword uri = createUri(value);
            //DomainEventBus.INSTANCE.post(uriIllustrationRequestEvent);
            return uri;
        } else {
            final Keyword concept = createConcept(value, steambeatLanguage);
            final ConceptIllustrationRequestEvent conceptIllustrationRequestEvent = new ConceptIllustrationRequestEvent(concept.getReferenceId(), concept.getValue());
            DomainEventBus.INSTANCE.post(conceptIllustrationRequestEvent);
            return concept;
        }
    }

    private Keyword createUri(final String value) {
        try {
            final Reference reference = referenceService.newReference();
            final List<String> tokens = uriManager.getTokens(value);
            final List<Keyword> keywords = Lists.newArrayList();
            for (final String token : tokens) {
                try {
                    keywords.add(lookUp(token, SteambeatLanguage.none()));
                } catch (KeywordNotFound e) {
                    keywords.add(createKeyword(token, SteambeatLanguage.none(), reference.getId()));
                }
            }
            final KeywordMerger keywordMerger = new KeywordMerger();
            keywordMerger.merge(keywords);
            return keywords.get(0);
        } catch (UriException e) {
            e.printStackTrace();
            return createConcept(value, SteambeatLanguage.none());
        }
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
    }

    public Keyword createKeyword(final String value, final SteambeatLanguage steambeatLanguage, final UUID referenceID) {
        final Keyword keyword = keywordFactory.createKeyword(value, steambeatLanguage, referenceID);
        Repositories.keywords().add(keyword);
        return keyword;
    }

    public Keyword lookUpOrCreate(final String value, final String languageCode) {
        Keyword keyword;
        try {
            keyword = lookUp(value, SteambeatLanguage.forString(languageCode));
        } catch (KeywordNotFound e) {
            keyword = createKeyword(value, SteambeatLanguage.forString(languageCode));
        }
        return keyword;
    }

    public static boolean isUri(final String text) {
        return URI_PATTERN.matcher(text).matches();
    }

    private ReferenceService referenceService;
    private final KeywordFactory keywordFactory;
    private Translator translator;
    private UriManager uriManager;
    private static final Pattern URI_PATTERN = Pattern.compile("((http|https)://)?([%a-zA-Z_0-9\\.-]+)(\\.[a-z]{2,3}){1}(/.*$)?", Pattern.CASE_INSENSITIVE);
}
