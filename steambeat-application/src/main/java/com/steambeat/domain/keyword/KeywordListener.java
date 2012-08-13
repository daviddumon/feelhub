package com.steambeat.domain.keyword;

import com.google.common.eventbus.*;
import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.thesaurus.Language;
import com.steambeat.domain.translation.TranslationDoneEvent;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class KeywordListener {

    @Inject
    public KeywordListener(final KeywordFactory keywordFactory) {
        this.keywordFactory = keywordFactory;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handle(final TranslationDoneEvent event) {
        final UUID topicId = lookUpTopic(event.getResult());
        if (topicId != null) {
            event.getKeyword().setTopic(topicId);
        } else {
            final Keyword keyword = keywordFactory.createKeyword(event.getResult(), Language.reference(), event.getKeyword().getTopic());
            Repositories.keywords().add(keyword);
        }
    }

    private UUID lookUpTopic(final String result) {
        final Keyword keyword = Repositories.keywords().forValueAndLanguage(result, Language.reference());
        if (keyword != null) {
            return keyword.getTopic();
        }
        return null;
    }

    private KeywordFactory keywordFactory;
}
