package com.feelhub.sitemap.converter;


import com.feelhub.sitemap.domain.*;
import com.feelhub.test.SystemTime;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class SitemapToStringConverterTest {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canConvert() {
        time.set(new DateTime(2013, 3, 06, 12, 15));
        Sitemap sitemap = new Sitemap(Lists.<SitemapEntry>newArrayList(new SitemapEntry("/feeling92", Frequency.hourly, 0.8)));

        String result = new SitemapToStringConverter(sitemap).toString();

        assertThat(result).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<urlset xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\" xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n" +
                "<url>\n" +
                "<loc>toto:8080/feeling92</loc>\n" +
                "<lastmod>2013-03-06T12:15:00.000+01:00</lastmod>\n" +
                "<changefreq>hourly</changefreq>\n" +
                "<priority>0.8</priority>\n" +
                "</url>\n" +
                "</urlset>");
    }
}
