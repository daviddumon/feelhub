package com.feelhub.sitemap.domain;

import com.google.common.collect.Lists;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class SitemapIndexTest {
    @Test
    public void canGetLoc() {
        SitemapIndex sitemapIndex = new SitemapIndex(Lists.<Sitemap>newArrayList());
        sitemapIndex.setIndex(123);

        assertThat(sitemapIndex.getLoc()).isEqualTo("/sitemap_index_00123.xml");
    }
}
