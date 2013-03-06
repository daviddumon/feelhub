package com.feelhub.sitemap.domain;

import com.feelhub.test.SystemTime;
import com.google.common.collect.Lists;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SitemapTest {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void hasLastMod() {
        SitemapEntry oldEntry = new SitemapEntry("one", Frequency.hourly, 0.5);
        time.waitDays(1);
        final SitemapEntry newEntry = new SitemapEntry("older", Frequency.hourly, 0.5);

        final Sitemap sitemap = new Sitemap(Lists.newArrayList(oldEntry, newEntry));

        assertThat(sitemap.getLastMod(), is(newEntry.getLastMod()));
    }

}
