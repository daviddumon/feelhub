package com.steambeat.test.testFactories;

import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.feed.Feed;
import com.steambeat.repositories.Repositories;
import com.steambeat.domain.opinion.*;

public class OpinionFactoryForTest {

    public Opinion newOpinion() {
        final Feed feed = TestFactories.feeds().newFeed();
        final Opinion opinion = feed.createOpinion("myopinion", Feeling.good);
        Repositories.opinions().add(opinion);
        return opinion;
    }

    public Opinion newOpinionForSubject(final Subject subject) {
        final Opinion opinion = ((Feed) subject).createOpinion("myopinion", Feeling.good);
        Repositories.opinions().add(opinion);
        return opinion;
    }
}
