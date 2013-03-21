package com.feelhub.analytic;

import com.feelhub.analytic.live.LiveDailyStatisticsWorker;
import com.feelhub.analytic.user.UserStatisticsWorker;
import com.google.inject.AbstractModule;

public class AnalyticWorkersModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(LiveDailyStatisticsWorker.class).asEagerSingleton();
        bind(UserStatisticsWorker.class).asEagerSingleton();
    }
}
