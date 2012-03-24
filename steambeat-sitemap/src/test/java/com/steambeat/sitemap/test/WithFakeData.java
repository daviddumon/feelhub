package com.steambeat.sitemap.test;

import com.steambeat.sitemap.domain.*;
import org.junit.*;
import org.junit.rules.ExternalResource;

public class WithFakeData extends ExternalResource {

    @Before
    public void before() {
        Sitemap.setCapacity(100);
        SitemapIndex.setCapacity(3);
        for (int i = 0; i < Math.round(Sitemap.getCapacity() * 4.4); i++) {
            SitemapEntryRepository.add(new SitemapEntry("sitemap" + i, Frequency.hourly, 0.5));
        }
        SitemapRepository.buildAllSitemaps();
        SitemapIndexRepository.buildAllSitemapIndexes();
    }

    @After
    public void after() {
        Sitemap.setCapacity(1000);
        SitemapIndex.setCapacity(1000);
        SitemapEntryRepository.clear();
        SitemapRepository.clear();
        SitemapIndexRepository.clear();
    }
}
