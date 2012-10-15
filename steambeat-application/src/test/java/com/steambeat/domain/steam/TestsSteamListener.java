package com.steambeat.domain.steam;

import com.steambeat.application.*;
import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.reference.ReferenceFactory;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.domain.translation.FakeTranslator;
import com.steambeat.domain.uri.*;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.*;
import com.steambeat.test.TestFactories;
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
        final Keyword steam = TestFactories.keywords().newKeyword("", SteambeatLanguage.none());
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

        final Keyword steamKeyword = Repositories.keywords().forValueAndLanguage("", SteambeatLanguage.none());
        assertThat(steamKeyword, notNullValue());
        final SteamStatisticsEvent steamStatisticsEvent = bus.lastEvent(SteamStatisticsEvent.class);
        assertThat(steamStatisticsEvent.getJudgment().getReferenceId(), is(steamKeyword.getReferenceId()));
    }

    private SteamListener steamListener;
}
