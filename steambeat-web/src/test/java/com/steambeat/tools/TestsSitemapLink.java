package com.steambeat.tools;

import com.steambeat.test.FakeInternet;
import org.apache.commons.io.IOUtils;
import org.junit.*;

import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.notNullValue;

public class TestsSitemapLink {

    @Rule
    public static FakeInternet internet = new FakeInternet();

    //@AfterClass
    //public static void afterClass() {
    //    internet.stop();
    //}

    @Test
    public void canGetSitemap() throws Exception {
        final SitemapLink sitemapLink = new SitemapLink();

        final InputStream stream = sitemapLink.getSitemap("00001");

        assertThat(stream, notNullValue());
        final String sitemap = IOUtils.toString(stream);
        assertThat(sitemap, is("index : 00001"));
    }
}
