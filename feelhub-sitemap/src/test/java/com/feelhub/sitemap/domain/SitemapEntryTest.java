package com.feelhub.sitemap.domain;

import com.feelhub.test.SystemTime;
import org.joda.time.DateTime;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class SitemapEntryTest {

    @Test
    public void canCreateASitemapEntry() {
        final String uri = "http://www.fakeentry.com";

        final SitemapEntry sitemapEntry = new SitemapEntry(uri, Frequency.hourly, 0.8, DateTime.parse("01"));

        assertThat(sitemapEntry.getLoc()).isEqualTo(uri);
        assertThat(sitemapEntry.getFrequency()).isEqualTo(Frequency.hourly);
        assertThat(sitemapEntry.getPriority()).isEqualTo(0.8);
        assertThat(sitemapEntry.getLastMod()).isEqualTo(DateTime.parse("01"));
    }

}
