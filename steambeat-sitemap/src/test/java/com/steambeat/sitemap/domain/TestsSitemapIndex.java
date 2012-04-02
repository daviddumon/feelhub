package com.steambeat.sitemap.domain;

import com.steambeat.sitemap.test.WithFakeData;
import com.steambeat.test.SystemTime;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSitemapIndex {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithFakeData withFakeData = new WithFakeData();

    @Test
    public void canGetASitemapIndexForAnIndex() {
        final SitemapIndex sitemapIndex = new SitemapIndex(1);

        assertThat(sitemapIndex, notNullValue());
    }

    @Test
    public void knowsItsIndex() {
        final SitemapIndex sitemapIndex = new SitemapIndex(1);

        assertThat(sitemapIndex.getIndex(), is(1));
    }

    @Test
    public void hasALoc() {
        final SitemapIndex sitemapIndex = new SitemapIndex(1);

        assertThat(sitemapIndex.getLoc(), is("/sitemap_index_00001.xml"));
    }

    @Test
    public void hasGoodSitemaps() {
        final SitemapIndex sitemapIndex = new SitemapIndex(0);

        assertThat(sitemapIndex.getSitemaps(), notNullValue());
        assertThat(sitemapIndex.getSitemaps().size(), is(SitemapIndex.getCapacity()));
    }

    @Test
    public void canNotCreateASitemapIndexWithNegativeIndex() {
        exception.expect(SitemapIndexCreationException.class);

        new SitemapIndex(-1);
    }
}
