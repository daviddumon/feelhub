package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.subject.Subject;

public class WebPage extends Subject {

    // Mongolink constructor : do not delete
    public WebPage() {
    }

    public WebPage(final Association association) {
        super(association.getSubjectId().toString());
        this.uri = association.getId();
    }

    public Uri getRealUri() {
        return new Uri(uri);
    }

    private String uri;
}
