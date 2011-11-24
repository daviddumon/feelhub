package com.steambeat.test.testFactories;

import com.steambeat.domain.opinion.*;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;

public class OpinionFactoryForTest {

    public Opinion newOpinion() {
        final WebPage webPage = TestFactories.webPages().newWebPage();
        final Opinion opinion = webPage.createOpinion("my good opinion", Feeling.good);
        Repositories.opinions().add(opinion);
        return opinion;
    }

    public Opinion newOpinionWithText(final String text) {
        final WebPage webPage = TestFactories.webPages().newWebPage();
        final Opinion opinion = webPage.createOpinion(text, Feeling.good);
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
}
