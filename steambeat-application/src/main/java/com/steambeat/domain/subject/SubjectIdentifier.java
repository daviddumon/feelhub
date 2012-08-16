package com.steambeat.domain.subject;

import com.google.common.eventbus.*;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.KeywordCreatedEvent;
import com.steambeat.domain.subject.uri.*;

import java.util.regex.Pattern;

public class SubjectIdentifier {

    public SubjectIdentifier() {
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handle(final KeywordCreatedEvent event) {
        final String value = event.getKeyword().getValue();

        DomainEventBus.INSTANCE.post(new UriCreatedEvent(new Uri("")));
    }

    public static boolean isUri(final String value) {
        return URI_PATTERN.matcher(value).matches();
    }

    public static final Pattern URI_PATTERN = Pattern.compile("((http|https)://)?([%a-zA-Z_0-9\\.-]+)(\\.[a-z]{2,3}){1}(/.*$)?", Pattern.CASE_INSENSITIVE);
}
