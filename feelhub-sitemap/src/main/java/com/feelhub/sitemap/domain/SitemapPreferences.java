package com.feelhub.sitemap.domain;

public class SitemapPreferences {

    public static void setSitemapCapacity(int sitemapCapacity) {
        SitemapPreferences.sitemapCapacity = sitemapCapacity;
    }

    public static int sitemapCapacity() {
        return sitemapCapacity;
    }

    public static void setSitemapIndexCapacity(int sitemapIndexCapacity) {
        SitemapPreferences.sitemapIndexCapacity = sitemapIndexCapacity;
    }

    public static int sitemapIndexCapacity() {
        return sitemapIndexCapacity;
    }

    private static int sitemapCapacity = 1000;
    private static int sitemapIndexCapacity = 1000;

}
