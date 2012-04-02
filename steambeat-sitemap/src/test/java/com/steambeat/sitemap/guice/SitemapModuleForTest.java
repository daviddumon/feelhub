package com.steambeat.sitemap.guice;

import com.google.inject.AbstractModule;
import com.steambeat.sitemap.application.SitemapScheduler;
import com.steambeat.sitemap.test.FakeSitemapScheduler;

public class SitemapModuleForTest extends AbstractModule {

    @Override
    protected void configure() {
        bind(SitemapScheduler.class).toInstance(new FakeSitemapScheduler());
    }
}
