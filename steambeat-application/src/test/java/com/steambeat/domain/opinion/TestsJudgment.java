package com.steambeat.domain.opinion;

import com.steambeat.domain.subject.Subject;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsJudgment {

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Test
    public void hasASubjectAndFeeling() {
        final Feeling feeling = Feeling.good;
        final Subject subject = TestFactories.subjects().newWebPage();

        final Judgment judgment = new Judgment(subject, feeling);

        assertThat(judgment, notNullValue());
        assertThat(judgment.getSubject(), is(subject));
        assertThat(judgment.getFeeling(), is(feeling));
    }
}
