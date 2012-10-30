package com.feelhub.sitemap.domain;

import com.feelhub.sitemap.test.WithFakeData;
import com.feelhub.test.SystemTime;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSitemapEntryRepository {

    @Rule
    public WithFakeData data = new WithFakeData();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canAddASitemapEntry() {
        data.clear();
        final SitemapEntry entry = new SitemapEntry("entry", Frequency.hourly, 0.8);
        final SitemapEntry otherEntry = new SitemapEntry("otherEntry", Frequency.hourly, 0.8);

        SitemapEntryRepository.add(entry);
        SitemapEntryRepository.add(otherEntry);

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

    @Test
    public void doNotAddAlreadyExistingTopics() {
        data.clear();
        final SitemapEntry firstSitemapEntry = new SitemapEntry("sitemap", Frequency.hourly, 0.5);
        final SitemapEntry anotherSitemapEntry = new SitemapEntry("sitemap", Frequency.always, 0.5);

        SitemapEntryRepository.add(firstSitemapEntry);
        SitemapEntryRepository.add(anotherSitemapEntry);

        assertThat(SitemapEntryRepository.getSitemapEntries().size(), is(1));
    }

    @Test
    public void canUpdateAnAlreadyExistingSitemapEntry() {
        data.clear();
        final SitemapEntry firstSitemapEntry = new SitemapEntry("sitemap", Frequency.hourly, 0.5);
        time.waitDays(1);
        final SitemapEntry anotherSitemapEntry = new SitemapEntry("sitemap", Frequency.always, 0.5);

        SitemapEntryRepository.add(firstSitemapEntry);
        SitemapEntryRepository.add(anotherSitemapEntry);

        assertThat(SitemapEntryRepository.getSitemapEntries().size(), is(1));
        assertThat(SitemapEntryRepository.get(firstSitemapEntry.getLoc()).getLastMod(), is(time.getNow()));
    }
}
