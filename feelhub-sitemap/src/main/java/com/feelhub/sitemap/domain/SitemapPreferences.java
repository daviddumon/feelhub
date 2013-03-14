package com.feelhub.sitemap.domain;

public class SitemapPreferences {

    public static void setSitemapCapacity(final int sitemapCapacity) {
        SitemapPreferences.sitemapCapacity = sitemapCapacity;
    }

    public static int sitemapCapacity() {
        return sitemapCapacity;
    }

    public static void setSitemapIndexCapacity(final int sitemapIndexCapacity) {
        SitemapPreferences.sitemapIndexCapacity = sitemapIndexCapacity;
    }

    public static int sitemapIndexCapacity() {
        return sitemapIndexCapacity;
    }

    private static int sitemapCapacity = 5000;
    private static int sitemapIndexCapacity = 5000;

}
