package com.steambeat.application;

import com.steambeat.domain.opinion.Feeling;
import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.Repositories;

public class OpinionService {

    public void addOpinion(final Subject subject, final Feeling feeling, final String text) {
        Repositories.opinions().add(subject.createOpinion(text, feeling));
    }
}
