package com.steambeat.application;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.domain.alchemy.AlchemyRequestEvent;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.illustration.*;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.domain.translation.Translator;
import com.steambeat.domain.uri.*;
import com.steambeat.repositories.Repositories;
import org.apache.commons.lang.WordUtils;

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

    public Keyword lookUpOrCreate(final String value, final String languageCode) {
        Keyword keyword;
        try {
            keyword = lookUp(value, SteambeatLanguage.forString(languageCode));
        } catch (KeywordNotFound e) {
            keyword = createKeyword(value, SteambeatLanguage.forString(languageCode));
        }
        return keyword;
    }

    public Keyword lookUpOrCreateSteam() {
        Keyword keyword = Repositories.keywords().forValueAndLanguage("", SteambeatLanguage.none());
        if (keyword == null) {
            return createKeyword("", SteambeatLanguage.none());
        }
        return keyword;
    }

    public Keyword lookUp(final String value, final SteambeatLanguage steambeatLanguage) {
        checkSize(value);
        Keyword keyword;
        if (KeywordService.isUri(value)) {
            keyword = Repositories.keywords().forValueAndLanguage(value, steambeatLanguage);
        } else {
            keyword = Repositories.keywords().forValueAndLanguage(normalize(value), steambeatLanguage);
        }
        if (keyword == null) {
            throw new KeywordNotFound();
        }
        return keyword;
    }

    private void checkSize(final String value) {
        // Note : there is not much sens in keeping words smaller than 3
        // but as we use a translation to reference keywords between eachother
        // we have to accept anything not empty
        // Only exception to this rule is the "steam" keyword that aggragates everything else
        if (value.isEmpty()) {
            throw new BadValueException();
        }
    }

    public Keyword lookUp(final UUID referenceId, final SteambeatLanguage language) {
        final Keyword keyword;
        final List<Keyword> keywords = Repositories.keywords().forReferenceId(referenceId);
        if (!keywords.isEmpty()) {
            keyword = getGoodKeyword(keywords, language);
        } else {
            // it should never happens!
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

    protected Keyword createKeyword(final String value, final SteambeatLanguage steambeatLanguage) {
        if (KeywordService.isUri(value)) {
            final Keyword uri = createUri(value);
            requestUriIllustration(uri);
            requestAlchemy(uri);
            return uri;
        } else if (!value.isEmpty()) {
            final Keyword concept = createConcept(normalize(value), steambeatLanguage);
            requestConceptIllustration(concept);
            return concept;
        } else {
            final Keyword steam = createKeyword("", SteambeatLanguage.none(), referenceService.newReference().getId());
            return steam;
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

    private void requestUriIllustration(final Keyword uri) {
        final UriIllustrationRequestEvent uriIllustrationRequestEvent = new UriIllustrationRequestEvent(uri.getReferenceId(), uri.getValue());
        DomainEventBus.INSTANCE.post(uriIllustrationRequestEvent);
    }

    private void requestAlchemy(final Keyword uri) {
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(uri);
        DomainEventBus.INSTANCE.post(alchemyRequestEvent);
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

    private void requestConceptIllustration(final Keyword concept) {
        final ConceptIllustrationRequestEvent conceptIllustrationRequestEvent = new ConceptIllustrationRequestEvent(concept.getReferenceId(), concept.getValue());
        DomainEventBus.INSTANCE.post(conceptIllustrationRequestEvent);
    }

    protected Keyword createKeyword(final String value, final SteambeatLanguage steambeatLanguage, final UUID referenceID) {
        final Keyword keyword = keywordFactory.createKeyword(value, steambeatLanguage, referenceID);
        Repositories.keywords().add(keyword);
        return keyword;
    }

    private String normalize(String value) {
        value = value.toLowerCase();
        value = WordUtils.capitalize(value);
        return value;
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
