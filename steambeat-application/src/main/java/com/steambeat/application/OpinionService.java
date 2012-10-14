package com.steambeat.application;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.opinion.*;
import com.steambeat.repositories.*;

import java.util.List;

public class OpinionService {

    @Inject
    public OpinionService(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        subjectExtractor = new SubjectExtractor();
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final OpinionRequestEvent opinionRequestEvent) {
        sessionProvider.start();
        final Opinion opinion = new Opinion(opinionRequestEvent.getText(), opinionRequestEvent.getUserId(), opinionRequestEvent.getOpinionId());
        opinion.setLanguageCode(opinionRequestEvent.getUserLanguageCode());
        final List<Subject> subjects = subjectExtractor.extract(opinionRequestEvent.getText());
        for (Subject subject : subjects) {
            // get the keyword with good reference
            // creation d'un jugement avec le feeling et le subject dans l'opinion
        }
        // creation de toutes les relations entre les jugements
        Repositories.opinions().add(opinion);
        sessionProvider.stop();
    }

    private SessionProvider sessionProvider;
    private final SubjectExtractor subjectExtractor;
}
