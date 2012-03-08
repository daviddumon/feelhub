package com.steambeat.test.testFactories;

import com.steambeat.domain.opinion.*;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;

public class OpinionFactoryForTest {

    public Opinion newOpinion() {
        return newOpinionWithText("my good opinion");
    }

    public Opinion newOpinionWithText(final String text) {
        return newOpinion(TestFactories.subjects().newWebPage(), text);
    }

    public void newOpinions(final int quantity) {
        final WebPage webPage = TestFactories.subjects().newWebPage();
        newOpinions(webPage, quantity);
    }

    public void newOpinions(final Subject subject, final int quantity) {
        for (int i = 0; i < quantity; i++) {
            newOpinion(subject, "i" + i);
        }
    }

    private Opinion newOpinion(final Subject subject, final String text) {
        final Opinion opinion = new Opinion(text);
        opinion.addJudgment(subject, Feeling.bad);
        Repositories.opinions().add(opinion);
        return opinion;
    }
}
