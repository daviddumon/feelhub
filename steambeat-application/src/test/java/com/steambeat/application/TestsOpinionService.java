package com.steambeat.application;

import com.google.common.collect.Lists;
import com.steambeat.application.dto.JudgmentDTO;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.topic.Topic;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.TestFactories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpinionService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canAddOpinionAndJudgements() {
        final OpinionService service = new OpinionService();
        final Topic topic = TestFactories.topics().newTopic();
        final JudgmentDTO judgmentDTO = new JudgmentDTO(topic, Feeling.good);

        service.addOpinion("Le texte de l'opinion", Lists.newArrayList(judgmentDTO));

        assertThat(Repositories.opinions().getAll().size(), is(1));
        final Opinion opinion = Repositories.opinions().getAll().get(0);
        assertThat(opinion.getText(), is("Le texte de l'opinion"));
        assertThat(opinion.getJudgments().size(), is(1));
        final Judgment judgment = opinion.getJudgments().get(0);
        assertThat(judgment.getTopic(), is(topic));
        assertThat(judgment.getFeeling(), is(Feeling.good));
    }
}
