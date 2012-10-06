package com.steambeat.application;

import com.steambeat.domain.opinion.*;
import com.steambeat.repositories.Repositories;

import java.util.List;

public class OpinionService {

    public void addOpinion(final String text, final List<Judgment> judgments) {
        final Opinion opinion = new Opinion(text);
        for (Judgment judgment : judgments) {
            opinion.addJudgment(judgment);
        }
        Repositories.opinions().add(opinion);
    }
}
