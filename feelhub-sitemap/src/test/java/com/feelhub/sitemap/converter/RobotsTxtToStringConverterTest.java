package com.feelhub.sitemap.converter;

import com.feelhub.sitemap.domain.Sitemap;
import com.feelhub.sitemap.domain.SitemapIndex;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class RobotsTxtToStringConverterTest {

    @Test
    public void canConvertRobotsTxt() {
        List<SitemapIndex> indexes = Lists.newArrayList();
        SitemapIndex sitemapIndex = new SitemapIndex(Lists.<Sitemap>newArrayList());
        sitemapIndex.setIndex(123);
        indexes.add(sitemapIndex);
        SitemapIndex sitemapIndex1 = new SitemapIndex(Lists.<Sitemap>newArrayList());
        sitemapIndex1.setIndex(145);
        indexes.add(sitemapIndex1);

        String robots = new RobotsTxtToStringConverter(indexes).toString();

        assertThat(robots).isEqualTo("User-agent: *\n" + "Disallow:\n" + "Sitemap: toto:8080/sitemap_index_00123.xml\n" + "Sitemap: toto:8080/sitemap_index_00145.xml\n");
    }
}
