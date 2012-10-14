package com.steambeat.domain;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.concept.ConceptEvent;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.uri.UriEvent;
import com.steambeat.repositories.SessionProvider;

import java.util.regex.Pattern;

public class SubjectIdentifier {

    @Inject
    public SubjectIdentifier(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
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
        final UriEvent event = new UriEvent(keyword);
        DomainEventBus.INSTANCE.post(event);
    }

    private void createConcept(final Keyword keyword) {
        final ConceptEvent event = new ConceptEvent();
        event.addIfAbsent(keyword);
        DomainEventBus.INSTANCE.post(event);
    }

    public static boolean isUri(final Keyword keyword) {
        return URI_PATTERN.matcher(keyword.getValue()).matches();
    }

    public static boolean isUri(final String text) {
        return URI_PATTERN.matcher(text).matches();
    }

    private final SessionProvider sessionProvider;

    private static final Pattern URI_PATTERN = Pattern.compile("((http|https)://)?([%a-zA-Z_0-9\\.-]+)(\\.[a-z]{2,3}){1}(/.*$)?", Pattern.CASE_INSENSITIVE);
}
