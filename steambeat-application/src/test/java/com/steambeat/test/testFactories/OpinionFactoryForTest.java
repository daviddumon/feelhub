package com.steambeat.test.testFactories;

import com.google.common.collect.Lists;
import com.steambeat.domain.opinion.Feeling;
import com.steambeat.domain.opinion.Judgment;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;

import java.util.List;

public class OpinionFactoryForTest {

    public Opinion newOpinion() {
        return newOpinionWithText("my good opinion");
    }

    public Opinion newOpinionWithText(final String text) {
        return newOpinion(TestFactories.webPages().newWebPage(), text);
    }

    public void newOpinions(final int quantity) {
        final WebPage webPage = TestFactories.webPages().newWebPage();
        newOpinions(webPage, quantity);
    }

    public void newOpinions(final Subject subject, final int quantity) {
        for (int i = 0; i < quantity; i++) {
            newOpinion(subject, "i" + i);
        }
    }

    private Opinion newOpinion(Subject subject, String text) {
        final Opinion opinion = new Opinion(text);
        opinion.addJudgment(subject, Feeling.bad);
        Repositories.opinions().add(opinion);
        return opinion;
    }

    private List<Judgment> getJudgments() {
        final List<Judgment> judgments = Lists.newArrayList();
        final Judgment judgment = TestFactories.judgments().newJudgment();
        judgments.add(judgment);
        return judgments;
    }
}
