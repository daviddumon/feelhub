package com.feelhub.sitemap.domain;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class SitemapIndexBuilderTest {

    @Test
    public void canBuildSitemapIndexes() {
        SitemapPreferences.setSitemapCapacity(3);
        SitemapPreferences.setSitemapIndexCapacity(2);
        List<SitemapEntry> entries = createEntries(7);

        List<SitemapIndex> sitemapIndexes = new SitemapIndexBuilder().build(entries);

        assertThat(sitemapIndexes).hasSize(2);
        SitemapIndex firstIndex = sitemapIndexes.get(0);
        assertThat(firstIndex.getSitemaps()).hasSize(2);
        assertThat(firstIndex.getSitemaps().get(0).getEntries()).hasSize(3);
        SitemapIndex secondIndex = sitemapIndexes.get(1);
        assertThat(secondIndex.getSitemaps()).hasSize(1);
        assertThat(secondIndex.getSitemaps().get(0).getEntries()).hasSize(2);
    }

    private List<SitemapEntry> createEntries(int count) {
        List<SitemapEntry> entries = Lists.newArrayList();
        for (int i = 0; i < count; i++) {
            entries.add(new SitemapEntry("sitemap" + i, Frequency.hourly, 0.5));
        }
        return entries;
    }

    @Test
    public void addARootEntry() {
        List<SitemapIndex> sitemapIndexes = new SitemapIndexBuilder().build(Lists.<SitemapEntry>newArrayList());

        assertThat(sitemapIndexes).hasSize(1);
        SitemapIndex firstIndex = sitemapIndexes.get(0);
        assertThat(firstIndex.getSitemaps()).hasSize(1);
        assertThat(firstIndex.getSitemaps().get(0).getEntries()).hasSize(1);
        assertThat(firstIndex.getSitemaps().get(0).getEntries().get(0)).isEqualTo(new SitemapEntry("", Frequency.hourly, 0.8));
    }

}
