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
        if (SubjectIdentifier.isUri(keyword.getValue())) {
            createUri();
        } else {
            createConcept(keyword);
        }
        sessionProvider.stop();
    }

    private void createUri() {
        DomainEventBus.INSTANCE.post(new UriCreatedEvent(new Uri("")));
    }

    private void createConcept(final Keyword keyword) {
        final Concept concept = new Concept();
        concept.addIfAbsent(keyword);
        DomainEventBus.INSTANCE.post(new ConceptCreatedEvent(concept));
    }

    public static boolean isUri(final String value) {
        return URI_PATTERN.matcher(value).matches();
    }

    private SessionProvider sessionProvider;

    public static final Pattern URI_PATTERN = Pattern.compile("((http|https)://)?([%a-zA-Z_0-9\\.-]+)(\\.[a-z]{2,3}){1}(/.*$)?", Pattern.CASE_INSENSITIVE);
}
