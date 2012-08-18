package com.steambeat.domain;

import com.google.common.eventbus.*;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.concept.*;
import com.steambeat.domain.uri.*;

import java.util.regex.Pattern;

public class SubjectIdentifier {

    public SubjectIdentifier() {
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handle(final KeywordCreatedEvent event) {
        final Keyword keyword = event.getKeyword();
        if (SubjectIdentifier.isUri(keyword.getValue())) {
            createUri();
        } else {
            createConcept(keyword);
        }
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

    public static final Pattern URI_PATTERN = Pattern.compile("((http|https)://)?([%a-zA-Z_0-9\\.-]+)(\\.[a-z]{2,3}){1}(/.*$)?", Pattern.CASE_INSENSITIVE);
}
