package com.feelhub.sitemap.domain;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class SitemapIndexBuilderTest {

    @Test
    public void canBuildSitemapIndexes() {
        SitemapPreferences.setSitemapCapacity(3);
        SitemapPreferences.setSitemapIndexCapacity(2);
        final List<SitemapEntry> entries = createEntries(7);

        final List<SitemapIndex> sitemapIndexes = new SitemapIndexBuilder().build(entries);

        assertThat(sitemapIndexes).hasSize(2);
        final SitemapIndex firstIndex = sitemapIndexes.get(0);
        assertThat(firstIndex.getSitemaps()).hasSize(2);
        assertThat(firstIndex.getSitemaps().get(0).getEntries()).hasSize(3);
        final SitemapIndex secondIndex = sitemapIndexes.get(1);
        assertThat(secondIndex.getSitemaps()).hasSize(1);
        assertThat(secondIndex.getSitemaps().get(0).getEntries()).hasSize(2);
    }

    @Test
    public void addARootEntry() {
        SitemapPreferences.setSitemapIndexCapacity(2);
        SitemapPreferences.setSitemapCapacity(2);

        final List<SitemapIndex> sitemapIndexes = new SitemapIndexBuilder().build(createEntries(0));

        assertThat(sitemapIndexes).hasSize(1);
        final SitemapIndex firstIndex = sitemapIndexes.get(0);
        assertThat(firstIndex.getSitemaps()).hasSize(1);
        assertThat(firstIndex.getSitemaps().get(0).getEntries()).hasSize(1);
        assertThat(firstIndex.getSitemaps().get(0).getEntries().get(0)).isEqualTo(new SitemapEntry("", Frequency.hourly, 0.8, DateTime.now()));
    }

    @Test
    public void indexAreSet() {
        SitemapPreferences.setSitemapIndexCapacity(2);
        SitemapPreferences.setSitemapCapacity(1);

        final List<SitemapIndex> sitemapIndexes = new SitemapIndexBuilder().build(createEntries(1));

        assertThat(sitemapIndexes.get(0).getIndex()).isEqualTo(0);
        assertThat(sitemapIndexes.get(0).getSitemaps().get(0).getIndex()).isEqualTo(0);
        assertThat(sitemapIndexes.get(0).getSitemaps().get(1).getIndex()).isEqualTo(1);
    }

    private List<SitemapEntry> createEntries(final int count) {
        final List<SitemapEntry> entries = Lists.newArrayList();
        for (int i = 0; i < count; i++) {
            entries.add(new SitemapEntry("sitemap" + i, Frequency.hourly, 0.5, DateTime.now()));
        }
        return entries;
    }
}
