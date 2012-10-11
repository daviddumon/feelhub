package com.steambeat.application;

import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.opinion.*;
import com.steambeat.repositories.Repositories;

import java.util.List;

public class OpinionService {

    public Opinion addOpinion(final String text, final List<Judgment> judgments, final String languageCode) {
        final Opinion opinion = new Opinion(text);
        opinion.setLanguageCode(languageCode);
        for (Judgment judgment : judgments) {
            opinion.addJudgment(judgment);
        }
        Repositories.opinions().add(opinion);
        DomainEventBus.INSTANCE.post(new OpinionCreatedEvent(opinion));
        return opinion;
    }
}
