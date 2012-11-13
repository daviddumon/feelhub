package com.feelhub.application;

import com.feelhub.domain.alchemy.AlchemyRequestEvent;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.illustration.UriIllustrationRequestEvent;
import com.feelhub.domain.keyword.*;
import com.feelhub.domain.keyword.uri.*;
import com.feelhub.domain.keyword.word.Word;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.translation.Translator;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.apache.commons.lang.WordUtils;

import java.util.*;

public class KeywordService {

    @Inject
    public KeywordService(final TopicService topicService, final KeywordFactory keywordFactory, final Translator translator, final UriManager uriManager) {
        this.topicService = topicService;
        this.keywordFactory = keywordFactory;
        this.translator = translator;
        this.uriManager = uriManager;
    }

    public Keyword lookUpOrCreate(final String value, final String languageCode) {
        Keyword keyword;
        try {
            keyword = lookUp(value, FeelhubLanguage.forString(languageCode));
        } catch (KeywordNotFound e) {
            keyword = createKeyword(value, FeelhubLanguage.forString(languageCode));
        }
        return keyword;
    }

    public Keyword lookUp(final String value, final FeelhubLanguage feelhubLanguage) {
        checkSize(value);
        final Keyword keyword;
        if (KeywordIdentifier.isUri(value)) {
            keyword = Repositories.keywords().forValueAndLanguage(value, feelhubLanguage);
        } else {
            keyword = Repositories.keywords().forValueAndLanguage(normalizeWord(value), feelhubLanguage);
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
        // Only exception to this rule is the "world" keyword that aggragates everything else
        if (value.isEmpty()) {
            throw new BadValueException();
        }
    }

    public Keyword lookUp(final UUID topicId, final FeelhubLanguage language) {
        final Keyword keyword;
        final List<Keyword> keywords = Repositories.keywords().forTopicId(topicId);
        if (!keywords.isEmpty()) {
            keyword = getGoodKeyword(keywords, language);
        } else {
            // it should never happens!
            keyword = new Word("?", language, topicId);
        }
        return keyword;
    }

    private Keyword getGoodKeyword(final List<Keyword> keywords, final FeelhubLanguage feelhubLanguage) {
        Keyword referenceKeyword = null;
        for (final Keyword keyword : keywords) {
            if (keyword.getLanguage().equals(feelhubLanguage)) {
                return keyword;
            } else if (keyword.getLanguage().equals(FeelhubLanguage.reference())) {
                referenceKeyword = keyword;
            }
        }
        if (referenceKeyword != null) {
            return referenceKeyword;
        } else {
            return keywords.get(0);
        }
    }

    protected Keyword createKeyword(final String value, final FeelhubLanguage feelhubLanguage) {
        if (KeywordIdentifier.isUri(value)) {
            final Keyword uri = createUri(value);
            requestUriIllustration(uri);
            requestAlchemy(uri);
            return uri;
        } else if (!value.isEmpty()) {
            final Keyword concept = createWord(normalizeWord(value), feelhubLanguage);
            requestConceptIllustration(concept);
            return concept;
        } else {
            final Keyword world = createWord("", FeelhubLanguage.none(), topicService.newTopic().getId());
            return world;
        }
    }

    private Keyword createUri(final String value) {
        try {
            final Topic topic = topicService.newTopic();
            final List<String> tokens = uriManager.getTokens(value);
            final List<Keyword> keywords = Lists.newArrayList();
            for (final String token : tokens) {
                try {
                    keywords.add(lookUp(token, FeelhubLanguage.none()));
                } catch (KeywordNotFound e) {
                    keywords.add(createUri(token, FeelhubLanguage.none(), topic.getId()));
                }
            }
            final KeywordMerger keywordMerger = new KeywordMerger();
            keywordMerger.merge(keywords);
            return keywords.get(0);
        } catch (UriException e) {
            e.printStackTrace();
            return createWord(value, FeelhubLanguage.none());
        }
    }

    private Keyword createUri(final String value, final FeelhubLanguage none, final UUID topicId) {
        final Keyword keyword = keywordFactory.createUri(value, topicId);
        Repositories.keywords().add(keyword);
        return keyword;
    }

    private void requestUriIllustration(final Keyword uri) {
        final UriIllustrationRequestEvent uriIllustrationRequestEvent = new UriIllustrationRequestEvent(uri.getTopicId(), uri.getValue());
        DomainEventBus.INSTANCE.post(uriIllustrationRequestEvent);
    }

    private void requestAlchemy(final Keyword uri) {
        final AlchemyRequestEvent alchemyRequestEvent = new AlchemyRequestEvent(uri);
        DomainEventBus.INSTANCE.post(alchemyRequestEvent);
    }

    private Keyword createWord(final String value, final FeelhubLanguage feelhubLanguage) {
        if (!feelhubLanguage.equals(FeelhubLanguage.reference()) && !feelhubLanguage.equals(FeelhubLanguage.none())) {
            try {
                final String translatedValue = translator.translateToEnglish(value, feelhubLanguage);
                try {
                    final Keyword referenceKeyword = lookUp(translatedValue, FeelhubLanguage.reference());
                    return createWord(value, feelhubLanguage, referenceKeyword.getTopicId());
                } catch (KeywordNotFound e) {
                    final Topic topic = topicService.newTopic();
                    createWord(translatedValue, FeelhubLanguage.reference(), topic.getId());
                    return createWord(value, feelhubLanguage, topic.getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
                final Topic topic = topicService.newTopic();
                final Word word = createWord(value, feelhubLanguage, topic.getId());
                word.setTranslationNeeded(true);
                return word;
            }
        } else {
            return createWord(value, feelhubLanguage, topicService.newTopic().getId());
        }
    }

    private void requestConceptIllustration(final Keyword concept) {
        //final WordIllustrationRequestEvent wordIllustrationRequestEvent = new WordIllustrationRequestEvent(concept.getTopicId(), concept.getValue());
        //DomainEventBus.INSTANCE.post(wordIllustrationRequestEvent);
    }

    protected Word createWord(final String value, final FeelhubLanguage feelhubLanguage, final UUID topicId) {
        final Word word = keywordFactory.createWord(value, feelhubLanguage, topicId);
        Repositories.keywords().add(word);
        return word;
    }

    private String normalizeWord(String value) {
        value = value.toLowerCase();
        value = WordUtils.capitalize(value);
        return value;
    }

    private TopicService topicService;
    private final KeywordFactory keywordFactory;
    private Translator translator;
    private UriManager uriManager;
}
