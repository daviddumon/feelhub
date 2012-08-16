package com.steambeat.domain.keyword;

import com.google.common.eventbus.*;
import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.thesaurus.Language;
import com.steambeat.domain.translation.TranslationDoneEvent;
import com.steambeat.repositories.Repositories;

public class KeywordListener {

    @Inject
    public KeywordListener(final KeywordFactory keywordFactory) {
        this.keywordFactory = keywordFactory;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handle(final TranslationDoneEvent event) {
        final Keyword referenceKeyword = lookUpReferenceKeyword(event.getResult());
        if (referenceKeyword != null) {
            event.getKeyword().setTopicId(referenceKeyword.getTopicId());
        } else {
            final Keyword keyword = keywordFactory.createKeyword(event.getResult(), Language.reference(), event.getKeyword().getTopicId());
            Repositories.keywords().add(keyword);
        }
    }

    private Keyword lookUpReferenceKeyword(final String result) {
        final Keyword keyword = Repositories.keywords().forValueAndLanguage(result, Language.reference());
        if (keyword != null) {
            return keyword;
        }
        return null;
    }

    private KeywordFactory keywordFactory;
}
