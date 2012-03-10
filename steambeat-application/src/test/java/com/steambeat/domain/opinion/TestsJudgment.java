package com.steambeat.domain.opinion;

import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.TestWithMongoRepository;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsJudgment extends TestWithMongoRepository {

    @Test
    public void hasASubjectAndFeeling() {
        final Subject subject = TestFactories.subjects().newWebPage();
        final Feeling feeling = Feeling.good;

        final Judgment judgment = new Judgment(subject, feeling);

        assertThat(judgment, notNullValue());
        assertThat(judgment.getSubject(), is(subject));
        assertThat(judgment.getFeeling(), is(feeling));
    }
}
