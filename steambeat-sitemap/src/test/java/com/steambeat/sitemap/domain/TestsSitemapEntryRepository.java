package com.steambeat.sitemap.domain;

import com.steambeat.sitemap.test.WithFakeData;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSitemapEntryRepository {

    @Rule
    public WithFakeData withFakeData = new WithFakeData();

    @Test
    public void canAddASitemapEntry() {
        SitemapEntryRepository.clear();
        final SitemapEntry entry = new SitemapEntry("entry", Frequency.hourly, 0.8);

        SitemapEntryRepository.add(entry);
        SitemapEntryRepository.add(entry);

        assertThat(SitemapEntryRepository.getSitemapEntries().size(), is(2));
    }

    @Test
    public void canClear() {
        final SitemapEntry entry = new SitemapEntry("entry", Frequency.hourly, 0.8);
        SitemapEntryRepository.add(entry);
        SitemapEntryRepository.add(entry);

        SitemapEntryRepository.clear();

        assertThat(SitemapEntryRepository.getSitemapEntries().size(), is(0));
    }

    @Test
    public void canGetEntriesForIndex() {
        final List<SitemapEntry> entries = SitemapEntryRepository.getSitemapEntriesFor(new Sitemap(1));

        assertThat(entries, notNullValue());
        assertThat(entries.size(), is(Sitemap.getCapacity()));
    }

    @Test
    public void canGetEntriesForWrongIndexes() {
        assertThat(SitemapEntryRepository.getSitemapEntriesFor(new Sitemap(30)), notNullValue());
    }

    @Test
    public void canMakeGoodPartition() {
        assertThat(SitemapEntryRepository.getSitemapEntriesFor(new Sitemap(0)).size(), is(100));
        assertThat(SitemapEntryRepository.getSitemapEntriesFor(new Sitemap(1)).size(), is(100));
        assertThat(SitemapEntryRepository.getSitemapEntriesFor(new Sitemap(4)).size(), is(40));
        assertThat(SitemapEntryRepository.getSitemapEntriesFor(new Sitemap(5)).size(), is(0));
    }

    @Test
    public void canGetASitemapEntryByLoc() {
        final SitemapEntry sitemapEntry = new SitemapEntry("test", Frequency.always, 0.7);
        SitemapEntryRepository.add(sitemapEntry);

        final SitemapEntry found = SitemapEntryRepository.get(sitemapEntry.getLoc());

        assertThat(found, is(sitemapEntry));
    }
}
