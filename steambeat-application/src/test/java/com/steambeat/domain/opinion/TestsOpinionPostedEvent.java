package com.steambeat.domain.opinion;

import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.*;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpinionPostedEvent {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void hasADate() {
        final Opinion opinion = TestFactories.opinions().newOpinion();

        final OpinionPostedEvent event = new OpinionPostedEvent(opinion);

        assertThat(event.getDate(), is(time.getNow()));
    }
}
