package com.steambeat.domain.uri;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.concept.ConceptEvent;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.SessionProvider;

import java.util.List;

public class UriManager {

    @Inject
    public UriManager(final SessionProvider sessionProvider, final UriResolver uriResolver, final KeywordService keywordService) {
        this.sessionProvider = sessionProvider;
        this.uriResolver = uriResolver;
        this.keywordService = keywordService;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final UriEvent event) {
        sessionProvider.start();
        try {
            final List<String> uris = uriResolver.resolve(event.getKeyword().getValue());
            final List<Keyword> keywords = getKeywordsFor(uris);
            postCompleteUriEvent(keywords);
        } catch (UriException e) {
            postConceptCreatedEvent(event);
        }
        sessionProvider.stop();
    }

    private List<Keyword> getKeywordsFor(final List<String> uris) {
        final List<Keyword> result = Lists.newArrayList();
        for (final String uri : uris) {
            final UriTokenizer uriTokenizer = new UriTokenizer();
            final List<String> tokens = uriTokenizer.getTokensFor(uri);
            for (final String token : tokens) {
                result.add(getOrCreateKeyword(token));
            }
        }
        return result;
    }

    private Keyword getOrCreateKeyword(final String token) {
        try {
            return keywordService.lookUp(token.toString(), SteambeatLanguage.none());
        } catch (KeywordNotFound e) {
            return keywordService.createKeywordWithoutEvent(token.toString(), SteambeatLanguage.none());
        }
    }

    private void postCompleteUriEvent(final List<Keyword> keywords) {
        final CompleteUriEvent completeUriEvent = new CompleteUriEvent();
        completeUriEvent.addAllAbsent(keywords);
        DomainEventBus.INSTANCE.post(completeUriEvent);
    }

    private void postConceptCreatedEvent(final UriEvent event) {
        final ConceptEvent conceptEvent = new ConceptEvent();
        conceptEvent.addIfAbsent(event.getKeyword());
        DomainEventBus.INSTANCE.post(conceptEvent);
    }

    private SessionProvider sessionProvider;
    private UriResolver uriResolver;
    private KeywordService keywordService;
}
