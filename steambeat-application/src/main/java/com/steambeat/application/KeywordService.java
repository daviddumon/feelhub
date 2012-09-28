package com.steambeat.application;

import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;

import java.util.*;

public class KeywordService {

    @Inject
    public KeywordService(final KeywordFactory keywordFactory, final ReferenceService referenceService) {
        this.keywordFactory = keywordFactory;
        this.referenceService = referenceService;
    }

    public Keyword lookUp(final String value, final SteambeatLanguage steambeatLanguage) {
        final Keyword keyword = Repositories.keywords().forValueAndLanguage(value, steambeatLanguage);
        if (keyword == null) {
            throw new KeywordNotFound();
        }
        return keyword;
    }

    public Keyword createKeyword(final String value, final SteambeatLanguage steambeatLanguage) {
        final Keyword keyword = createKeywordWithoutEvent(value, steambeatLanguage);
        postEvent(keyword);
        return keyword;
    }

    public Keyword createKeywordWithoutEvent(final String value, final SteambeatLanguage steambeatLanguage) {
        final Reference reference = referenceService.newReference();
        final Keyword keyword = keywordFactory.createKeyword(value, steambeatLanguage, reference.getId());
        Repositories.keywords().add(keyword);
        return keyword;
    }

    public List<Keyword> lookUpAll(final UUID referenceId) {
        return Repositories.keywords().forReferenceId(referenceId);
    }

    private void postEvent(final Keyword keyword) {
        DomainEventBus.INSTANCE.post(new KeywordCreatedEvent(keyword));
    }

    private final KeywordFactory keywordFactory;
    private final ReferenceService referenceService;
}
