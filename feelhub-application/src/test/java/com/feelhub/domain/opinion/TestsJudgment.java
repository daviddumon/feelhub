package com.feelhub.domain.opinion;

import com.feelhub.domain.reference.Reference;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsJudgment {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void hasAReferenceAndAFeeling() {
        final Reference reference = TestFactories.references().newReference();
        final Feeling feeling = Feeling.good;

        final Judgment judgment = new Judgment(reference.getId(), feeling);

        assertThat(judgment, notNullValue());
        assertThat(judgment.getReferenceId(), is(reference.getId()));
        assertThat(judgment.getFeeling(), is(feeling));
    }
}
