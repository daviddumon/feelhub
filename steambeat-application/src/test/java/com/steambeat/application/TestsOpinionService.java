package com.steambeat.application;

import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.feed.Feed;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.domain.opinion.*;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpinionService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canAddOpinion() {
        final Feed feed = TestFactories.feeds().newFeed();
        final OpinionService service = new OpinionService();

        service.addOpinion(feed, Feeling.bad, "this is your mother the text stfu noob");

        assertThat(Repositories.opinions().getAll().size(), is(1));
        final Opinion opinion = Repositories.opinions().getAll().get(0);
        assertThat(opinion.getSubject(), is((Subject) feed));
        assertThat(opinion.getText(), is("this is your mother the text stfu noob"));
        assertThat(opinion.getFeeling(), is(Feeling.bad));
    }
}
