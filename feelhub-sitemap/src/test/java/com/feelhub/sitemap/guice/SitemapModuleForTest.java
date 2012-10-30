package com.feelhub.sitemap.guice;

import com.google.inject.AbstractModule;
import com.feelhub.sitemap.application.SitemapScheduler;
import com.feelhub.sitemap.test.FakeSitemapScheduler;

public class SitemapModuleForTest extends AbstractModule {

    @Override
    protected void configure() {
        bind(SitemapScheduler.class).toInstance(new FakeSitemapScheduler());
    }
}
