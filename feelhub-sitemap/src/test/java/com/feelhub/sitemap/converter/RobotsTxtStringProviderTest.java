package com.feelhub.sitemap.converter;

import com.feelhub.sitemap.domain.Sitemap;
import com.feelhub.sitemap.domain.SitemapIndex;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class RobotsTxtStringProviderTest {

    @Test
    public void canConvertRobotsTxt() {
        List<SitemapIndex> indexes = Lists.newArrayList();
        indexes.add(new SitemapIndex(Lists.<Sitemap>newArrayList()));
        indexes.add(new SitemapIndex(Lists.<Sitemap>newArrayList()));

        String robots = new RobotsTxtStringProvider().toString(indexes);

        assertThat(robots).isEqualTo("User-agent: *\n" + "Disallow:\n" + "Sitemap: localhost:8080/sitemap_index_00000.xml\n" + "Sitemap: localhost:8080/sitemap_index_00001.xml\n");
    }
}
