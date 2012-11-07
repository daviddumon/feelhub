package com.feelhub.domain.world;

import com.feelhub.application.*;
import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.keyword.*;
import com.feelhub.domain.opinion.*;
import com.feelhub.domain.reference.ReferenceFactory;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.translation.FakeTranslator;
import com.feelhub.domain.uri.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsWorldListener {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        worldListener = new WorldListener(new FakeSessionProvider(), new KeywordService(new ReferenceService(new ReferenceFactory()), new KeywordFactory(), new FakeTranslator(), new UriManager(new FakeUriResolver())));
    }

    @Test
    public void addPostEventForWorldStatisticsOnJudgmentEvent() {
        bus.capture(WorldStatisticsEvent.class);
        final Keyword world = TestFactories.keywords().newKeyword("", FeelhubLanguage.none());
        final Judgment judgment = TestFactories.judgments().newJudgment();
        final JudgmentStatisticsEvent judgmentStatisticsEvent = new JudgmentStatisticsEvent(judgment);

        DomainEventBus.INSTANCE.post(judgmentStatisticsEvent);

        final WorldStatisticsEvent worldStatisticsEvent = bus.lastEvent(WorldStatisticsEvent.class);
        assertThat(worldStatisticsEvent, notNullValue());
        assertThat(worldStatisticsEvent.getJudgment(), notNullValue());
        assertThat(worldStatisticsEvent.getJudgment().getReferenceId(), is(world.getReferenceId()));
        assertThat(worldStatisticsEvent.getJudgment().getFeeling(), is(judgment.getFeeling()));
    }

    @Test
    public void canCreateWorldIfNotPresent() {
        bus.capture(WorldStatisticsEvent.class);
        final Judgment judgment = TestFactories.judgments().newJudgment();
        final JudgmentStatisticsEvent judgmentStatisticsEvent = new JudgmentStatisticsEvent(judgment);

        DomainEventBus.INSTANCE.post(judgmentStatisticsEvent);

        final Keyword worldKeyword = Repositories.keywords().forValueAndLanguage("", FeelhubLanguage.none());
        assertThat(worldKeyword, notNullValue());
        final WorldStatisticsEvent worldStatisticsEvent = bus.lastEvent(WorldStatisticsEvent.class);
        assertThat(worldStatisticsEvent.getJudgment().getReferenceId(), is(worldKeyword.getReferenceId()));
    }

    private WorldListener worldListener;
}
