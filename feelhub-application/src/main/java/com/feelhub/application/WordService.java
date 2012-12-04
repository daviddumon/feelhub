package com.feelhub.application;

import com.feelhub.domain.tag.TagFactory;
import com.feelhub.domain.translation.Translator;
import com.google.inject.Inject;

public class WordService {

    @Inject
    public WordService(final TagFactory tagFactory, final TopicService topicService, final Translator translator) {
        this.tagFactory = tagFactory;
        this.topicService = topicService;
        this.translator = translator;
    }
    //
    //public Word lookUpOrCreate(final String value, final FeelhubLanguage feelhubLanguage) {
    //    return lookUpOrCreate(value, feelhubLanguage, "");
    //}
    //
    //public Word lookUpOrCreate(final String value, final FeelhubLanguage feelhubLanguage, final String type) {
    //    try {
    //        return lookUp(value, feelhubLanguage);
    //    } catch (WordNotFound e) {
    //        return createWord(value, feelhubLanguage, type);
    //    }
    //}
    //
    //public Word lookUp(String value, final FeelhubLanguage language) {
    //    checkEmpty(value);
    //    value = normalize(value);
    //    final Word word = (Word) Repositories.tags().forValueAndLanguage(value, language);
    //    if (word == null) {
    //        throw new WordNotFound();
    //    }
    //    return word;
    //}
    //
    //private void checkEmpty(final String value) {
    //    if (value.isEmpty()) {
    //        throw new BadTagValueException();
    //    }
    //}
    //
    //protected Word createWord(String value, final FeelhubLanguage feelhubLanguage) {
    //    return createWord(value, feelhubLanguage, "");
    //}
    //
    //protected Word createWord(String value, final FeelhubLanguage feelhubLanguage, final String type) {
    //    value = normalize(value);
    //    Word word;
    //    if (!feelhubLanguage.isReference() && !feelhubLanguage.isNone()) {
    //        try {
    //            final String translatedValue = translator.translateToEnglish(value, feelhubLanguage);
    //            try {
    //                final Word referenceWord = lookUp(translatedValue, FeelhubLanguage.reference());
    //                word = createWord(value, feelhubLanguage, referenceWord.getTopicId());
    //            } catch (WordNotFound e) {
    //                word = createReferenceWord(value, feelhubLanguage, translatedValue);
    //            }
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //            final Topic topic = topicService.createTopic();
    //            word = createWord(value, feelhubLanguage, topic.getId());
    //            word.setTranslationNeeded(true);
    //        }
    //    } else {
    //        word = createWord(value, feelhubLanguage, topicService.createTopic().getId());
    //    }
    //    requestWordIllustration(word, type);
    //    return word;
    //}
    //
    //private Word createReferenceWord(final String value, final FeelhubLanguage feelhubLanguage, final String translatedValue) {
    //    final Topic topic = topicService.createTopic();
    //    createWord(translatedValue, FeelhubLanguage.reference(), topic.getId());
    //    return createWord(value, feelhubLanguage, topic.getId());
    //}
    //
    //private Word createWord(final String value, final FeelhubLanguage feelhubLanguage, final UUID topicId) {
    //    final Word word = tagFactory.createTag(value, feelhubLanguage, topicId);
    //    Repositories.tags().add(word);
    //    return word;
    //}
    //
    //private void requestWordIllustration(final Word word, final String type) {
    //    final WordIllustrationRequestEvent wordIllustrationRequestEvent = new WordIllustrationRequestEvent(word, type);
    //    DomainEventBus.INSTANCE.post(wordIllustrationRequestEvent);
    //}
    //
    //private String normalize(String value) {
    //    value = value.toLowerCase();
    //    value = WordUtils.capitalize(value);
    //    return value;
    //}

    private final TagFactory tagFactory;
    private final TopicService topicService;
    private final Translator translator;
}
