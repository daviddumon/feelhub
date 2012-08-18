package com.steambeat.domain.opinion;

import com.steambeat.test.*;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
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

        final JudgmentPostedEvent judgmentPostedEvent = new JudgmentPostedEvent(judgment);

        assertThat(judgmentPostedEvent.getDate(), notNullValue());
        assertThat(judgmentPostedEvent.getDate(), is(time.getNow()));
        assertThat(judgmentPostedEvent.getJudgment(), is(judgment));
    }
}
