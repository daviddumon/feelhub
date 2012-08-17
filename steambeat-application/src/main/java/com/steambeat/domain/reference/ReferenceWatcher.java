package com.steambeat.domain.reference;

import com.google.common.eventbus.*;
import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.domain.translation.TranslationDoneEvent;
import com.steambeat.repositories.Repositories;

public class ReferenceWatcher {

    @Inject
    public ReferenceWatcher(final KeywordFactory keywordFactory) {
        this.keywordFactory = keywordFactory;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handle(final TranslationDoneEvent event) {
        //final Keyword referenceKeyword = lookUpReferenceKeyword(event.getResult());
        //if (referenceKeyword != null) {
            //event.getKeyword().setReferenceId(referenceKeyword.getReferenceId());
        //} else {
            //final Keyword keyword = keywordFactory.createKeyword(event.getResult(), Language.reference(), event.getKeyword().getReferenceId());
            //Repositories.keywords().add(keyword);
        //}
    }

    private Keyword lookUpReferenceKeyword(final String result) {
        final Keyword keyword = Repositories.keywords().forValueAndLanguage(result, SteambeatLanguage.reference());
        if (keyword != null) {
            return keyword;
        }
        return null;
    }

    private KeywordFactory keywordFactory;
}
