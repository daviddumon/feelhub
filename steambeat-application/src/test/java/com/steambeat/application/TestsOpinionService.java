package com.steambeat.application;

import com.google.common.collect.Lists;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpinionService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canAddOpinionAndJudgements() {
        final Subject subject = TestFactories.webPages().newWebPage();
        final OpinionService service = new OpinionService();
        final JudgmentDTO judgmentDTO = new JudgmentDTO(subject, Feeling.good);

        service.addOpinion("Le texte de l'opinion", Lists.newArrayList(judgmentDTO));

        assertThat(Repositories.opinions().getAll().size(), is(1));
        final Opinion opinion = Repositories.opinions().getAll().get(0);
        assertThat(opinion.getText(), is("Le texte de l'opinion"));
        assertThat(opinion.getJudgments().size(), is(1));
        final Judgment judgment = opinion.getJudgments().get(0);
        assertThat(judgment.getSubject(), is(subject));
        assertThat(judgment.getFeeling(), is(Feeling.good));

    }
}
