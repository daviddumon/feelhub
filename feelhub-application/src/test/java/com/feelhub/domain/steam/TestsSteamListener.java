package com.feelhub.domain.steam;

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

public class TestsSteamListener {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        steamListener = new SteamListener(new FakeSessionProvider(), new KeywordService(new ReferenceService(new ReferenceFactory()), new KeywordFactory(), new FakeTranslator(), new UriManager(new FakeUriResolver())));
    }

    @Test
    public void addPostEventForSteamStatisticsOnJudgmentEvent() {
        bus.capture(SteamStatisticsEvent.class);
        final Keyword steam = TestFactories.keywords().newKeyword("", FeelhubLanguage.none());
        final Judgment judgment = TestFactories.judgments().newJudgment();
        final JudgmentStatisticsEvent judgmentStatisticsEvent = new JudgmentStatisticsEvent(judgment);

        DomainEventBus.INSTANCE.post(judgmentStatisticsEvent);

        final SteamStatisticsEvent steamStatisticsEvent = bus.lastEvent(SteamStatisticsEvent.class);
        assertThat(steamStatisticsEvent, notNullValue());
        assertThat(steamStatisticsEvent.getJudgment(), notNullValue());
        assertThat(steamStatisticsEvent.getJudgment().getReferenceId(), is(steam.getReferenceId()));
        assertThat(steamStatisticsEvent.getJudgment().getFeeling(), is(judgment.getFeeling()));
    }

    @Test
    public void canCreateSteamIfNotPresent() {
        bus.capture(SteamStatisticsEvent.class);
        final Judgment judgment = TestFactories.judgments().newJudgment();
        final JudgmentStatisticsEvent judgmentStatisticsEvent = new JudgmentStatisticsEvent(judgment);

        DomainEventBus.INSTANCE.post(judgmentStatisticsEvent);

        final Keyword steamKeyword = Repositories.keywords().forValueAndLanguage("", FeelhubLanguage.none());
        assertThat(steamKeyword, notNullValue());
        final SteamStatisticsEvent steamStatisticsEvent = bus.lastEvent(SteamStatisticsEvent.class);
        assertThat(steamStatisticsEvent.getJudgment().getReferenceId(), is(steamKeyword.getReferenceId()));
    }

    private SteamListener steamListener;
}
