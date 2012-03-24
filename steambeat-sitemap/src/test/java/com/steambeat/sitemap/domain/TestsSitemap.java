package com.steambeat.sitemap.domain;

import com.steambeat.sitemap.test.WithFakeData;
import com.steambeat.test.SystemTime;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSitemap {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithFakeData withFakeData = new WithFakeData();

    @Test
    public void hasALoc() {
        Sitemap sitemap = new Sitemap(1);

        assertThat(sitemap.getLoc(), is("http://www.steambeat.com/sitemap_00001.xml"));
    }

    @Test
    public void knowsItsIndex() {
        final Sitemap sitemap = new Sitemap(1);
        
        assertThat(sitemap.getIndex(), is(1));
    }

    @Test
    public void hasSitemapEntries() {
        Sitemap sitemap = new Sitemap(1);

        assertThat(sitemap.getEntries(), notNullValue());
        assertThat(sitemap.getEntries().size(), is(Sitemap.getCapacity()));
    }

    @Test
    public void hasLastMod() {
        time.waitDays(1);
        SitemapEntryRepository.add(new SitemapEntry("one", Frequency.hourly, 0.5));
        time.waitDays(1);
        final SitemapEntry older = new SitemapEntry("older", Frequency.hourly, 0.5);
        SitemapEntryRepository.add(older);
        time.waitDays(1);

        Sitemap sitemap = new Sitemap(4);
        
        assertThat(sitemap.getLastMod(), is(older.getLastMod()));
    }

    @Test
    public void canNotCreateASitemapWithNegativeIndex() {
        exception.expect(SitemapCreationException.class);

        new Sitemap(-1);
    }
}
