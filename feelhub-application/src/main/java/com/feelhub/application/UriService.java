package com.feelhub.application;

import com.feelhub.domain.alchemy.AlchemyRequestEvent;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.illustration.UriIllustrationRequestEvent;
import com.feelhub.domain.keyword.*;
import com.feelhub.domain.keyword.uri.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.*;

public class UriService {

    @Inject
    public UriService(final KeywordFactory keywordFactory, final TopicService topicService, final UriManager uriManager) {
        this.keywordFactory = keywordFactory;
        this.topicService = topicService;
        this.uriManager = uriManager;
    }

    public Uri lookUpOrCreate(final String value) {
        try {
            return lookUp(value);
        } catch (UriNotFound e) {
            return createUri(value);
        }
    }

    public Uri lookUp(final String value) {
        checkEmpty(value);
        final Uri uri = (Uri) Repositories.keywords().forValueAndLanguage(value, FeelhubLanguage.none());
        if (uri == null) {
            throw new UriNotFound();
        }
        return uri;
    }

    private void checkEmpty(final String value) {
        if (value.isEmpty()) {
            throw new BadValueException();
        }
    }

    public Uri createUri(final String value) {
        try {
            final Topic topic = topicService.newTopic();
            final List<String> tokens = uriManager.getTokens(value);
            final List<Keyword> keywords = Lists.newArrayList();
            for (final String token : tokens) {
                try {
                    keywords.add(lookUp(token));
                } catch (KeywordNotFound e) {
                    keywords.add(createUri(token, topic.getId()));
                }
            }
            final KeywordMerger keywordMerger = new KeywordMerger();
            keywordMerger.merge(keywords);
            final Uri uri = (Uri) keywords.get(0);
            requestUriIllustration(uri);
            requestAlchemy(uri);
            return uri;
        } catch (UriException e) {
            e.printStackTrace();
            //return createWord(value, FeelhubLanguage.none());
            return null;
        }
    }

    private Keyword createUri(final String value, final UUID topicId) {
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

    private KeywordFactory keywordFactory;
    private TopicService topicService;
    private UriManager uriManager;
}
