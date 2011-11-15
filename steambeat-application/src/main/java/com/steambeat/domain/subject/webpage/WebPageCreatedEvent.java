package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.DomainEvent;
import org.joda.time.DateTime;

public class WebPageCreatedEvent implements DomainEvent {

    public WebPageCreatedEvent(final WebPage result) {
        this.webPage = result;
    }

    @Override
    public DateTime getDate() {
        return date;
    }

    public WebPage getWebPage() {
        return webPage;
    }

    private final WebPage webPage;
    private final DateTime date = new DateTime();
}
