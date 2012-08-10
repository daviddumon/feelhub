package com.steambeat.domain.opinion;

import com.steambeat.test.SystemTime;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

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
