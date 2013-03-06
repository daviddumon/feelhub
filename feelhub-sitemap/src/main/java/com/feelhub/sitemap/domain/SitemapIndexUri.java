package com.feelhub.sitemap.domain;

public class SitemapIndexUri {
    public static String getUri(int index) {
        return "/sitemap_index_" + String.format("%05d", index) + ".xml";
    }
}
