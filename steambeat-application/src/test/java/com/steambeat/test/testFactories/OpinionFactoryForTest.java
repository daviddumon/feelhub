package com.steambeat.test.testFactories;

import com.steambeat.domain.opinion.*;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;

import java.util.ArrayList;

public class OpinionFactoryForTest {

    public Opinion newOpinion() {
        final ArrayList<Judgment> judgments = getJudgments();
        final Opinion opinion = new Opinion("my good opinion", judgments);
        Repositories.opinions().add(opinion);
        return opinion;
    }

    public Opinion newOpinionWithText(final String text) {
        final ArrayList<Judgment> judgments = getJudgments();
        final Opinion opinion = new Opinion(text, judgments);
        Repositories.opinions().add(opinion);
        return opinion;
    }

    public void newOpinions(final int quantity) {
        final WebPage webPage = TestFactories.webPages().newWebPage();
        newOpinions(webPage, quantity);
    }

    public void newOpinions(final Subject subject, final int quantity) {
        for (int i = 0; i < quantity; i++) {
            final Opinion opinion = subject.createOpinion("i" + i, Feeling.good);
            Repositories.opinions().add(opinion);
        }
    }

    private ArrayList<Judgment> getJudgments() {
        final ArrayList<Judgment> judgments = new ArrayList<Judgment>();
        final Judgment judgment = TestFactories.judgments().newJudgment();
        judgments.add(judgment);
        return judgments;
    }
}
