package com.steambeat.domain.opinion;

import com.steambeat.test.SystemTime;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TestsOpinionPostedEvent {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void hasADate() {
        final Opinion opinion = TestFactories.opinions().newOpinion();

        final OpinionPostedEvent event = new OpinionPostedEvent(opinion);

        assertThat(event.getDate(), is(time.getNow()));
    }
}
