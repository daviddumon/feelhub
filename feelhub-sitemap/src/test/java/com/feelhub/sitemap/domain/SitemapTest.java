package com.feelhub.sitemap.domain;

import com.feelhub.test.SystemTime;
import com.google.common.collect.Lists;
import org.junit.*;

import static org.fest.assertions.Assertions.*;


public class SitemapTest {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void hasLastMod() {
        SitemapEntry oldEntry = new SitemapEntry("one", Frequency.hourly, 0.5);
        time.waitDays(1);
        final SitemapEntry newEntry = new SitemapEntry("older", Frequency.hourly, 0.5);

        final Sitemap sitemap = new Sitemap(Lists.newArrayList(oldEntry, newEntry));

        assertThat(sitemap.getLastMod()).isEqualTo(newEntry.getLastMod());
    }

    @Test
    public void canGetLoc() {
        final Sitemap sitemap = new Sitemap(Lists.<SitemapEntry>newArrayList());
        sitemap.setIndex(124);

        assertThat(sitemap.getLoc()).isEqualTo("/sitemap_00124.xml");
    }
}
