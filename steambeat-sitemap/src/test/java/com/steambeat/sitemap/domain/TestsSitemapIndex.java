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
    public void canAddASitemaop() {
        final Sitemap sitemap = new Sitemap(1);

        sitemapIndex.add(sitemap);

        assertThat(sitemapIndex.getSitemaps().size(), is(1));
        assertThat(sitemapIndex.getSitemaps().get(0), is(sitemap));
    }

    @Test
    public void lastModDateChangeWhenAddNewSitemap() {
        time.waitDays(1);
        final Sitemap sitemap = new Sitemap(1);

        sitemapIndex.add(sitemap);

        assertThat(sitemapIndex.getLastModTime(), is(time.getNow()));
    }

    @Test
    public void throwACapacityExceptionIfAddingMoreThanCapacity() {
        exception.expect(CapacityException.class);
        final Sitemap sitemap = new Sitemap(1);
        SitemapIndex.setSitemapIndexCapacity(2);

        sitemapIndex.add(sitemap);
        sitemapIndex.add(sitemap);
        sitemapIndex.add(sitemap);
    }

    @Test
    public void canGetLastSitemapOfIndex() {
        final Sitemap sitemap = new Sitemap(1);
        final Sitemap lastSitemap = new Sitemap(2);

        sitemapIndex.add(sitemap);
        sitemapIndex.add(lastSitemap);

        assertThat(sitemapIndex.getLastSitemap(), is(lastSitemap));
    }

    private SitemapIndex sitemapIndex;
}
