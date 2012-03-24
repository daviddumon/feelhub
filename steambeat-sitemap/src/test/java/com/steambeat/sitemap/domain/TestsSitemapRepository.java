package com.steambeat.sitemap.domain;

import com.steambeat.sitemap.test.WithFakeData;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSitemapRepository {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public WithFakeData fakeData = new WithFakeData();

    @Test
    public void canBuildSitemapsListFromSitemapEntries() {
        assertThat(SitemapRepository.getSitemaps(), notNullValue());
        assertThat(SitemapRepository.getSitemaps().size(), is(5));
    }

    @Test
    public void canGetASitemapFromIndex() {
        SitemapRepository.buildAllSitemaps();

        final Sitemap sitemap = SitemapRepository.getSitemap(1);

        assertThat(sitemap, notNullValue());
    }

    @Test
    public void thrownExceptionForUnknownSitemap() {
        exception.expect(SitemapNotFoundException.class);

        SitemapRepository.getSitemap(10);
    }

    @Test
    public void canClearSitemapsList() {
        SitemapRepository.clear();

        assertThat(SitemapRepository.getSitemaps().size(), is(0));
    }

    @Test
    public void canGetSitemapsForSitemapIndex() {
        final SitemapIndex sitemapIndex = new SitemapIndex(1);

        final List<Sitemap> sitemaps = SitemapRepository.getSitemapsFor(sitemapIndex);

        assertThat(sitemaps, notNullValue());
        assertThat(sitemaps.size(), is(2));
    }

    @Test
    public void canGetEntriesForWrongIndexes() {
        assertThat(SitemapRepository.getSitemapsFor(new SitemapIndex(30)), notNullValue());
    }

    @Test
    public void canMakeGoodPartition() {
        assertThat(SitemapRepository.getSitemapsFor(new SitemapIndex(0)).size(), is(3));
        assertThat(SitemapRepository.getSitemapsFor(new SitemapIndex(1)).size(), is(2));
        assertThat(SitemapRepository.getSitemapsFor(new SitemapIndex(4)).size(), is(0));
    }
}
