package com.feelhub.domain.steam;

import com.feelhub.application.KeywordService;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.opinion.*;
import com.feelhub.repositories.SessionProvider;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

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
        final Keyword steam = keywordService.lookUpOrCreateSteam();
        final Judgment judgment = new Judgment(steam.getReferenceId(), judgmentStatisticsEvent.getJudgment().getFeeling());
        final SteamStatisticsEvent steamStatisticsEvent = new SteamStatisticsEvent(judgment);
        DomainEventBus.INSTANCE.post(steamStatisticsEvent);
        sessionProvider.stop();
    }

    private final SessionProvider sessionProvider;
    private final KeywordService keywordService;
}
