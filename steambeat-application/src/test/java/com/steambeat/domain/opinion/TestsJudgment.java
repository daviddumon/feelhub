package com.steambeat.domain.opinion;

import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsJudgment {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void hasASubjectAndFeeling() {
        final Reference reference = TestFactories.references().newReference();
        final Feeling feeling = Feeling.good;

        final Judgment judgment = new Judgment(reference, feeling);

        assertThat(judgment, notNullValue());
        assertThat(judgment.getReference(), is(reference));
        assertThat(judgment.getFeeling(), is(feeling));
    }
}
