package com.steambeat.sitemap.domain;

import com.steambeat.test.SystemTime;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsSitemapIndex {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void setUp() {
        sitemapIndex = new SitemapIndex(1);
    }

    @Test
    public void canGetPath() {
        assertThat(sitemapIndex.getPath(), is("http://www.steambeat.com/sitemap_index_00001.xml"));
    }

    @Test
    public void hasALastModDate() {
        assertThat(sitemapIndex.getLastModTime(), is(time.getNow()));
    }

    @Test
    public void canCreateASitemap() {
        final Sitemap sitemap = sitemapIndex.newSitemap();

        assertThat(sitemapIndex.getSitemaps().size(), is(1));
        assertThat(sitemapIndex.getSitemaps().get(0), is(sitemap));
    }

    @Test
    public void canCreateTwoSitemaps() {
        sitemapIndex.newSitemap();
        sitemapIndex.newSitemap();
        
        assertThat(sitemapIndex.getSitemaps().size(), is(2));
        assertThat(sitemapIndex.getSitemaps().get(0).getPath(), is("http://www.steambeat.com/sitemap_00001.xml"));
        assertThat(sitemapIndex.getSitemaps().get(1).getPath(), is("http://www.steambeat.com/sitemap_00002.xml"));
    }

    @Test
    public void lastModDateChangeWhenAddNewSitemap() {
        time.waitDays(1);

        sitemapIndex.newSitemap();

        assertThat(sitemapIndex.getLastModTime(), is(time.getNow()));
    }

    @Test
    public void throwACapacityExceptionIfAddingMoreThanCapacity() {
        exception.expect(CapacityException.class);
        SitemapIndex.setSitemapIndexCapacity(2);

        sitemapIndex.newSitemap();
        sitemapIndex.newSitemap();
        sitemapIndex.newSitemap();
    }

    @Test
    public void canGetLastSitemapOfIndex() {
        sitemapIndex.newSitemap();
        final Sitemap lastSitemap = sitemapIndex.newSitemap();

        assertThat(sitemapIndex.getLastSitemap(), is(lastSitemap));
    }

    private SitemapIndex sitemapIndex;
}
