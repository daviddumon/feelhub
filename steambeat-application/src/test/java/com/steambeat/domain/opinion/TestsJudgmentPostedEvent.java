package com.steambeat.domain.opinion;

import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.*;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsJudgmentPostedEvent {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canPostAJudgmentEvent() {
        final Judgment judgment = TestFactories.judgments().newJudgment();

        final JudgmentCreatedEvent judgmentCreatedEvent = new JudgmentCreatedEvent(judgment);

        assertThat(judgmentCreatedEvent.getDate(), notNullValue());
        assertThat(judgmentCreatedEvent.getDate(), is(time.getNow()));
        assertThat(judgmentCreatedEvent.getJudgment(), is(judgment));
    }
}
