package com.steambeat.domain.steam;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.SessionProvider;

public class SteamListener {

    @Inject
    public SteamListener(final SessionProvider sessionProvider, final KeywordService keywordService) {
        this.sessionProvider = sessionProvider;
        this.keywordService = keywordService;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final JudgmentStatisticsEvent judgmentStatisticsEvent) {
        sessionProvider.start();
        final Keyword steam = keywordService.lookUpOrCreate("", SteambeatLanguage.none().getCode());
        final Judgment judgment = new Judgment(steam.getReferenceId(), judgmentStatisticsEvent.getJudgment().getFeeling());
        final SteamStatisticsEvent steamStatisticsEvent = new SteamStatisticsEvent(judgment);
        DomainEventBus.INSTANCE.post(steamStatisticsEvent);
        sessionProvider.stop();
    }

    private final SessionProvider sessionProvider;
    private final KeywordService keywordService;
}
