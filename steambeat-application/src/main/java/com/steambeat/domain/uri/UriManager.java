package com.steambeat.domain.uri;

import com.google.common.eventbus.*;
import com.google.inject.Inject;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.concept.*;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.SessionProvider;

public class UriManager {

    @Inject
    public UriManager(final SessionProvider sessionProvider, final UriPathResolver uriPathResolver, final KeywordService keywordService) {
        this.sessionProvider = sessionProvider;
        this.uriPathResolver = uriPathResolver;
        this.keywordService = keywordService;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handle(UriCreatedEvent event) {
        sessionProvider.start();
        try {
            final Path path = uriPathResolver.resolve(event.getUri());
            postPathCreatedEvent(path);
        } catch (UriPathResolverException e) {
            postConceptCreatedEvent(event);
        }
        sessionProvider.stop();
    }

    private void postPathCreatedEvent(final Path path) {
        final PathCreatedEvent pathCreatedEvent = new PathCreatedEvent(path);
        DomainEventBus.INSTANCE.post(pathCreatedEvent);
    }

    private void extractKeywordsFromPath(final UriCreatedEvent event, final Path path) {
        for (Uri uri : path.getUris()) {
            if (!uri.equals(event.getUri())) {
                //event.getUri().addIfAbsent(getOrCreateKeyword(uri));
            }
        }
    }

    private Keyword getOrCreateKeyword(final Uri uri) {
        try {
            return keywordService.lookUp(uri.toString(), SteambeatLanguage.none());
        } catch (KeywordNotFound e) {
            return keywordService.createKeywordWithoutEvent(uri.toString(), SteambeatLanguage.none());
        }
    }

    private void postConceptCreatedEvent(final UriCreatedEvent event) {
        final Concept concept = new Concept();
        concept.addIfAbsent(event.getUri().getKeyword());
        final ConceptCreatedEvent conceptCreatedEvent = new ConceptCreatedEvent(concept);
        DomainEventBus.INSTANCE.post(conceptCreatedEvent);
    }

    private SessionProvider sessionProvider;
    private UriPathResolver uriPathResolver;
    private KeywordService keywordService;
}
