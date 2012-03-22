package com.steambeat.sitemap.domain;

import com.steambeat.sitemap.domain.*;
import com.steambeat.test.SystemTime;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsSitemap {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void setUp() {
        sitemap = new Sitemap(1);
    }

    @Test
    public void canGetPath() {
        assertThat(sitemap.getPath(), is("http://www.steambeat.com/sitemap_" + String.format("%05d", 1) + ".xml"));
    }

    @Test
    public void hasALastModDate() {
        assertThat(sitemap.getLastModTime(), is(time.getNow()));
    }

    @Test
    public void canAddAnEntry() {
        final SitemapEntry sitemapEntry = getEntry();

        sitemap.addEntry(sitemapEntry);

        assertThat(sitemap.getEntries().size(), is(1));
    }

    @Test
    public void lastModDateChangeWhenAddNewEntry() {
        time.waitDays(1);
        final SitemapEntry sitemapEntry = getEntry();

        sitemap.addEntry(sitemapEntry);

        assertThat(sitemap.getLastModTime(), is(time.getNow()));
    }

    @Test
    public void throwACapacityExceptionIfAddingMoreThanCapacity() {
        exception.expect(CapacityException.class);
        final SitemapEntry entry = getEntry();
        Sitemap.setSitemapCapacity(2);

        sitemap.addEntry(entry);
        sitemap.addEntry(entry);
        sitemap.addEntry(entry);
    }

    private SitemapEntry getEntry() {
        final String uri = "http://www.fakeentry.com";
        return new SitemapEntry(uri, Frequency.hourly, 0.5);
    }

    private Sitemap sitemap;
}
