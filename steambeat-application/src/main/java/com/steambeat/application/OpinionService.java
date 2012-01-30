package com.steambeat.application;

import com.steambeat.domain.opinion.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;

import java.util.List;

public class OpinionService {

    public void addOpinion(final String text, final List<JudgmentDTO> judgments) {
        final Opinion opinion = new Opinion(text);
        for (final JudgmentDTO judgment : judgments) {
            final WebPage webPage = Repositories.webPages().get(judgment.subjectId);
            if (webPage == null) {
                throw new WebPageNotYetCreatedException();
            }
            opinion.addJudgment(webPage, Feeling.valueOf(judgment.feeling));
        }
        Repositories.opinions().add(opinion);
    }
}
