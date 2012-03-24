package com.steambeat.sitemap.domain;

import com.google.common.collect.Lists;

import java.util.List;

public class SitemapIndexRepository {

    public static void buildAllSitemapIndexes() {
        final int numberOfSitemapIndexes = (int) Math.ceil((double) SitemapRepository.size() / SitemapIndex.getCapacity());
        for (int i = 0; i < numberOfSitemapIndexes; i++) {
            sitemapIndexes.add(new SitemapIndex(i));
        }
    }

    public static List<SitemapIndex> getSitemapIndexes() {
        return sitemapIndexes;
    }

    public static void clear() {
        sitemapIndexes.clear();
    }

    public static SitemapIndex getSitemapIndex(final int index) {
        if (index < sitemapIndexes.size()) {
            return sitemapIndexes.get(index);
        } else {
            throw new SitemapIndexNotFoundException();
        }
    }

    private static final List<SitemapIndex> sitemapIndexes = Lists.newArrayList();
}
