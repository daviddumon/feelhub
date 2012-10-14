package com.steambeat.domain.opinion;

import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.*;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsJudgmentStatisticsEvent {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canPostAJudgmentEvent() {
        final Judgment judgment = TestFactories.judgments().newJudgment();

        final JudgmentStatisticsEvent judgmentStatisticsEvent = new JudgmentStatisticsEvent(judgment);

        assertThat(judgmentStatisticsEvent.getDate(), notNullValue());
        assertThat(judgmentStatisticsEvent.getDate(), is(time.getNow()));
        assertThat(judgmentStatisticsEvent.getJudgment(), is(judgment));
    }
}