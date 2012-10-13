package com.steambeat.application;

import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.user.User;
import com.steambeat.repositories.Repositories;

import java.util.List;

public class OpinionService {

    public Opinion addOpinion(final String text, final List<Judgment> judgments, final String languageCode) {
        // TODO : prendre le user dans l'event
        final Opinion opinion = new Opinion(text, new User());
        opinion.setLanguageCode(languageCode);
        for (Judgment judgment : judgments) {
            opinion.addJudgment(judgment);
        }
        Repositories.opinions().add(opinion);
        DomainEventBus.INSTANCE.post(new OpinionCreatedEvent(opinion));
        return opinion;
    }
}
