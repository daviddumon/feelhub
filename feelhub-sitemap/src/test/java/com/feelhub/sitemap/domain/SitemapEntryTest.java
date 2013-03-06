package com.feelhub.sitemap.domain;

import com.feelhub.test.SystemTime;
import org.junit.*;

import static org.fest.assertions.Assertions.assertThat;

public class SitemapEntryTest {

    @Rule
    final public SystemTime time = SystemTime.fixed();

    @Test
    public void canCreateASitemapEntry() {
        final String uri = "http://www.fakeentry.com";

        final SitemapEntry sitemapEntry = new SitemapEntry(uri, Frequency.hourly, 0.8);

        assertThat(sitemapEntry.getLoc()).isEqualTo(uri);
        assertThat(sitemapEntry.getLastMod()).isEqualTo(time.getNow());
        assertThat(sitemapEntry.getFrequency()).isEqualTo(Frequency.hourly);
        assertThat(sitemapEntry.getPriority()).isEqualTo(0.8);
    }

}
