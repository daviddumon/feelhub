package com.steambeat.domain.opinion;

import com.steambeat.repositories.TestWithMongoRepository;
import com.steambeat.test.SystemTime;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsJudgmentPostedEvent extends TestWithMongoRepository {

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
