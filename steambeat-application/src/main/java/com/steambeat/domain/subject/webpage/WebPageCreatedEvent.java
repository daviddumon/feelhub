package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.eventbus.DomainEvent;
import org.joda.time.DateTime;

public class WebPageCreatedEvent implements DomainEvent {

    public WebPageCreatedEvent(final WebPage result) {
        this.webPage = result;
    }

    @Override
    public DateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("Webpage ");
        stringBuilder.append(webPage.getDescription());
        stringBuilder.append(" created. ");
        stringBuilder.append("<a href='/webpages/" + webPage.getId() + "'>link</a>");
        return stringBuilder.toString();
    }

    public WebPage getWebPage() {
        return webPage;
    }

    private final WebPage webPage;
    private final DateTime date = new DateTime();
}
