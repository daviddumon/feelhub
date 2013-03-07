package com.feelhub.sitemap.converter;


import com.feelhub.sitemap.domain.Sitemap;
import com.feelhub.sitemap.domain.SitemapEntry;
import com.feelhub.sitemap.domain.SitemapIndex;
import com.feelhub.test.SystemTime;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class SitemapIndexToStringConverterTest {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canConvert() {
        time.set(new DateTime(2013, 3, 06, 12, 15));
        Sitemap sitemap = new Sitemap(Lists.<SitemapEntry>newArrayList());
        sitemap.setIndex(67);
        SitemapIndex sitemapIndex = new SitemapIndex(Lists.newArrayList(sitemap));

        String result = new SitemapIndexToStringConverter(sitemapIndex).toString();

        assertThat(result).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<sitemapindex xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/siteindex.xsd\" xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n" +
                "<sitemap>\n" +
                "<loc>toto:8080/sitemap_00067.xml</loc>\n" +
                "<lastmod>2013-03-06T12:15:00.000+01:00</lastmod>\n" +
                "</sitemap>\n" +
                "</sitemapindex>");
    }

}
