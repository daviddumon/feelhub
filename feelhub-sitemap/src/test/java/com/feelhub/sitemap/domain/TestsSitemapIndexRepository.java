package com.feelhub.sitemap.domain;

import com.feelhub.sitemap.test.WithFakeData;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSitemapIndexRepository {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public WithFakeData fakeData = new WithFakeData();

    @Test
    public void canBuildSitemapIndexesFromSitemaps() {
        assertThat(SitemapIndexRepository.getSitemapIndexes(), notNullValue());
        assertThat(SitemapIndexRepository.getSitemapIndexes().size(), is(2));
    }

    @Test
    public void canGetASitemapIndexFromIndex() {
        final SitemapIndex sitemapIndex = SitemapIndexRepository.getSitemapIndex(0);

        assertThat(sitemapIndex, notNullValue());
        assertThat(sitemapIndex.getIndex(), is(0));
    }

    @Test
    public void thrownExceptionForUnknownSitemapIndex() {
        exception.expect(SitemapIndexNotFoundException.class);

        SitemapIndexRepository.getSitemapIndex(10);
    }

    @Test
    public void canClearSitemapIndexesList() {
        SitemapIndexRepository.clear();

        assertThat(SitemapIndexRepository.getSitemapIndexes().size(), is(0));
    }
}
