package com.steambeat.domain.opinion;

import com.google.common.collect.Lists;
import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.reference.*;
import com.steambeat.repositories.fakeRepositories.*;
import com.steambeat.test.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpinionManager {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        new OpinionManager(new FakeSessionProvider());
    }

    @Test
    public void canChangeJudgmentsReferencesForAConcept() {
        final Reference ref1 = TestFactories.references().newReference();
        final Reference ref2 = TestFactories.references().newReference();
        final Opinion op1 = TestFactories.opinions().newOpinionWithoutJudgments();
        final Opinion op2 = TestFactories.opinions().newOpinionWithoutJudgments();
        op1.addJudgment(ref1, Feeling.good);
        op2.addJudgment(ref1, Feeling.good);
        op1.addJudgment(ref2, Feeling.bad);
        op2.addJudgment(ref2, Feeling.bad);
        final ConceptReferencesChangedEvent event = TestFactories.events().newConceptReferencesChangedEvent(ref1.getId());
        event.addReferenceToChange(ref2.getId());

        DomainEventBus.INSTANCE.post(event);

        final List<Judgment> judgments = Lists.newArrayList();
        judgments.addAll(op1.getJudgments());
        judgments.addAll(op2.getJudgments());
        for (final Judgment judgment : judgments) {
            assertThat(judgment.getReferenceId(), is(ref1.getId()));
        }
    }
}
