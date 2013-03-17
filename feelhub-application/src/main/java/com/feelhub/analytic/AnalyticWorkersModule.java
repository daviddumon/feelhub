package com.feelhub.analytic;

import com.feelhub.analytic.daily.DailyStatisticsWorker;
import com.google.inject.AbstractModule;

public class AnalyticWorkersModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DailyStatisticsWorker.class).asEagerSingleton();
    }
}
