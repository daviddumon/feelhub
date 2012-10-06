package com.steambeat.application;

import com.google.common.collect.Lists;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpinionService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canAddOpinionAndJudgements() {
        final OpinionService service = new OpinionService();
        final Reference reference = TestFactories.references().newReference();
        final Judgment judgment = new Judgment(reference.getId(), Feeling.good);

        service.addOpinion("Le texte de l'opinion", Lists.newArrayList(judgment));

        assertThat(Repositories.opinions().getAll().size(), is(1));
        final Opinion opinion = Repositories.opinions().getAll().get(0);
        assertThat(opinion.getText(), is("Le texte de l'opinion"));
        assertThat(opinion.getJudgments().size(), is(1));
        assertThat(opinion.getJudgments().get(0), is(judgment));
    }
}
