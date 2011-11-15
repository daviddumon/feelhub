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

    public Opinion newOpinionForSubject(final Subject subject) {
        final Opinion opinion = subject.createOpinion("my good opinion", Feeling.good);
        Repositories.opinions().add(opinion);
        return opinion;
    }
}
