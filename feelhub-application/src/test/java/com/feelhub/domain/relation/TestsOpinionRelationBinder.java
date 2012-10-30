package com.feelhub.domain.relation;

import com.feelhub.domain.opinion.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsOpinionRelationBinder {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        opinionRelationBinder = new OpinionRelationBinder(new RelationBuilder(new RelationFactory()));
    }

    @Test
    public void canCreateRelationsForAnOpinion() {
        final Opinion opinion = TestFactories.opinions().newOpinionWithoutJudgments();
        final Judgment firstJudgment = TestFactories.judgments().newJudgment();
        final Judgment secondJudgment = TestFactories.judgments().newJudgment();
        final Judgment thirdJudgment = TestFactories.judgments().newJudgment();
        opinion.addJudgment(firstJudgment);
        opinion.addJudgment(secondJudgment);
        opinion.addJudgment(thirdJudgment);

        opinionRelationBinder.bind(opinion);

        final List<Relation> relations = Repositories.relations().getAll();
        assertThat(relations.size(), is(6));
        assertThat(relations.get(0).getWeight(), is(1.0));
        assertThat(relations.get(1).getWeight(), is(1.0));
        assertThat(relations.get(2).getWeight(), is(1.0));
        assertThat(relations.get(3).getWeight(), is(1.0));
        assertThat(relations.get(4).getWeight(), is(1.0));
        assertThat(relations.get(5).getWeight(), is(1.0));
    }

    private OpinionRelationBinder opinionRelationBinder;
}
