package com.steambeat.application;

import com.steambeat.domain.opinion.*;
import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpinionService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canAddOpinion() {
        final Subject subject = TestFactories.webPages().newWebPage();
        final OpinionService service = new OpinionService();

        service.addOpinion(subject, Feeling.bad, "my opinion");

        assertThat(Repositories.opinions().getAll().size(), is(1));
        final Opinion opinion = Repositories.opinions().getAll().get(0);
        assertThat(opinion.getSubject(), is(subject));
        assertThat(opinion.getText(), is("my opinion"));
        assertThat(opinion.getFeeling(), is(Feeling.bad));
    }
}
