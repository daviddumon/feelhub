package com.feelhub.sitemap.domain;

import com.feelhub.test.SystemTime;
import org.joda.time.DateTime;
import org.junit.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class SitemapEntryTest {

    @Rule
    final public SystemTime time = SystemTime.fixed();

    @Test
    public void canCreateASitemapEntry() {
        final String uri = "http://www.fakeentry.com";

        final SitemapEntry sitemapEntry = new SitemapEntry(uri, Frequency.hourly, 0.8);

        assertThat(sitemapEntry.getLoc(), is(uri));
        assertThat(sitemapEntry.getLastMod(), is(time.getNow()));
        assertThat(sitemapEntry.getFrequency(), is(Frequency.hourly));
        assertThat(sitemapEntry.getPriority(), is(0.8));
    }

    @Test
    public void canSetNewLastModDate() {
        final String uri = "http://www.fakeentry.com";
        final SitemapEntry sitemapEntry = new SitemapEntry(uri, Frequency.hourly, 0.8);
        final DateTime oldDate = sitemapEntry.getLastMod();
        time.waitDays(1);

        sitemapEntry.setLastMod(new DateTime());

        assertThat(oldDate, not(sitemapEntry.getLastMod()));
    }

    @Test
    public void equalsTo() {
        final SitemapEntry entry1 = new SitemapEntry("entry", Frequency.hourly, 0.1);
        final SitemapEntry entry2 = new SitemapEntry("entry", Frequency.always, 0.8);

        assertTrue(entry1.equals(entry2));
    }
}
