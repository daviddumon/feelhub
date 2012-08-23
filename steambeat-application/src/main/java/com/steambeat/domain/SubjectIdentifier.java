package com.steambeat.domain;

import com.google.common.eventbus.*;
import com.google.inject.Inject;
import com.steambeat.domain.concept.*;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.uri.*;
import com.steambeat.repositories.SessionProvider;

import java.util.regex.Pattern;

public class SubjectIdentifier {

    @Inject
    public SubjectIdentifier(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handle(final KeywordCreatedEvent event) {
        sessionProvider.start();
        final Keyword keyword = event.getKeyword();
        if (SubjectIdentifier.isUri(keyword)) {
            createUri(keyword);
        } else {
            createConcept(keyword);
        }
        sessionProvider.stop();
    }

    private void createUri(final Keyword keyword) {
        final Uri uri = new Uri(keyword.getValue());
        uri.setKeyword(keyword);
        DomainEventBus.INSTANCE.post(new UriCreatedEvent(uri));
    }

    private void createConcept(final Keyword keyword) {
        final Concept concept = new Concept();
        concept.addIfAbsent(keyword);
        DomainEventBus.INSTANCE.post(new ConceptCreatedEvent(concept));
    }

    public static boolean isUri(final Keyword keyword) {
        return URI_PATTERN.matcher(keyword.getValue()).matches();
    }

    private SessionProvider sessionProvider;

    private static final Pattern URI_PATTERN = Pattern.compile("((http|https)://)?([%a-zA-Z_0-9\\.-]+)(\\.[a-z]{2,3}){1}(/.*$)?", Pattern.CASE_INSENSITIVE);
}
