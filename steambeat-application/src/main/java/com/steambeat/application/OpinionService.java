package com.steambeat.application;

import com.steambeat.domain.opinion.*;
import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.Repositories;

import java.util.List;

public class OpinionService {

    //todo
    public void addOpinion(final Subject subject, final Feeling feeling, final String text) {
        Repositories.opinions().add(subject.createOpinion(text, feeling));
    }

    //todo a tester
    public void addOpinion(final String text, List<Judgment> judgments) {
        // envoyer les jugements vers leur service
        Repositories.opinions().add(new Opinion(text, judgments));
    }
}
