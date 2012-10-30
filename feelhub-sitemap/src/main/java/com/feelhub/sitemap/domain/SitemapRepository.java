package com.feelhub.sitemap.domain;

import com.google.common.collect.Lists;

import java.util.*;

public class SitemapRepository {

    public static void buildAllSitemaps() {
        final int numberOfSitemaps = (int) Math.ceil((double) SitemapEntryRepository.size() / Sitemap.getCapacity());
        for (int i = 0; i < numberOfSitemaps; i++) {
            sitemaps.add(new Sitemap(i));
        }
    }

    public static List<Sitemap> getSitemapsFor(final SitemapIndex sitemapIndex) {
        final int index = sitemapIndex.getIndex();
        final List<List<Sitemap>> partitions = Lists.partition(sitemaps, SitemapIndex.getCapacity());
        return index < partitions.size() ? partitions.get(index) : new ArrayList<Sitemap>();
    }

    public static List<Sitemap> getSitemaps() {
        return sitemaps;
    }

    public static Sitemap getSitemap(final int index) {
        try {
            return sitemaps.get(index);
        } catch (Exception e) {
            throw new SitemapNotFoundException();
        }
    }

    public static void clear() {
        sitemaps.clear();
    }

    public static int size() {
        return sitemaps.size();
    }

    private static final List<Sitemap> sitemaps = Lists.newArrayList();
}
