package com.feelhub.application;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.illustration.WordIllustrationRequestEvent;
import com.feelhub.domain.keyword.*;
import com.feelhub.domain.keyword.word.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.translation.Translator;
import com.feelhub.repositories.Repositories;
import com.google.inject.Inject;
import org.apache.commons.lang.WordUtils;

import java.util.UUID;

public class WordService {

    @Inject
    public WordService(final KeywordFactory keywordFactory, final TopicService topicService, final Translator translator) {
        this.keywordFactory = keywordFactory;
        this.topicService = topicService;
        this.translator = translator;
    }

    public Word lookUpOrCreate(final String value, final FeelhubLanguage feelhubLanguage) {
        return lookUpOrCreate(value, feelhubLanguage, "");
    }

    public Word lookUpOrCreate(final String value, final FeelhubLanguage feelhubLanguage, final String type) {
        try {
            return lookUp(value, feelhubLanguage);
        } catch (WordNotFound e) {
            return createWord(value, feelhubLanguage, type);
        }
    }

    public Word lookUp(String value, final FeelhubLanguage language) {
        checkEmpty(value);
        value = normalize(value);
        final Word word = (Word) Repositories.keywords().forValueAndLanguage(value, language);
        if (word == null) {
            throw new WordNotFound();
        }
        return word;
    }

    private void checkEmpty(final String value) {
        if (value.isEmpty()) {
            throw new BadValueException();
        }
    }

    protected Word createWord(String value, final FeelhubLanguage feelhubLanguage) {
        return createWord(value, feelhubLanguage, "");
    }

    protected Word createWord(String value, final FeelhubLanguage feelhubLanguage, final String type) {
        value = normalize(value);
        Word word;
        if (!feelhubLanguage.equals(FeelhubLanguage.reference()) && !feelhubLanguage.equals(FeelhubLanguage.none())) {
            try {
                final String translatedValue = translator.translateToEnglish(value, feelhubLanguage);
                try {
                    final Word referenceWord = lookUp(translatedValue, FeelhubLanguage.reference());
                    word = createWord(value, feelhubLanguage, referenceWord.getTopicId());
                } catch (WordNotFound e) {
                    final Topic topic = topicService.newTopic();
                    createWord(translatedValue, FeelhubLanguage.reference(), topic.getId());
                    word = createWord(value, feelhubLanguage, topic.getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
                final Topic topic = topicService.newTopic();
                word = createWord(value, feelhubLanguage, topic.getId());
                word.setTranslationNeeded(true);
            }
        } else {
            word = createWord(value, feelhubLanguage, topicService.newTopic().getId());
        }
        requestWordIllustration(word, type);
        return word;
    }

    private Word createWord(final String value, final FeelhubLanguage feelhubLanguage, final UUID topicId) {
        final Word word = keywordFactory.createWord(value, feelhubLanguage, topicId);
        Repositories.keywords().add(word);
        return word;
    }

    private void requestWordIllustration(final Word word, final String type) {
        final WordIllustrationRequestEvent wordIllustrationRequestEvent = new WordIllustrationRequestEvent(word, type);
        DomainEventBus.INSTANCE.post(wordIllustrationRequestEvent);
    }

    private String normalize(String value) {
        value = value.toLowerCase();
        value = WordUtils.capitalize(value);
        return value;
    }

    private final KeywordFactory keywordFactory;
    private final TopicService topicService;
    private final Translator translator;
}
