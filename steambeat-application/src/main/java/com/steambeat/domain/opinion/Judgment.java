package com.steambeat.domain.opinion;

import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;

public class Judgment {

    // Constructor for mapper : do not delete !
    protected Judgment() {
    }

    public Judgment(final Subject subject, final Feeling feeling) {
        this.subject = subject;
        this.feeling = feeling;
    }

    public Feeling getFeeling() {
        return feeling;
    }

    public WebPage getSubject() {
        return Repositories.webPages().get(subject.getId());
    }

    private Feeling feeling;
    private Subject subject;
}
