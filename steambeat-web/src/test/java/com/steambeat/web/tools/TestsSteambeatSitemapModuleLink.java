package com.steambeat.web.tools;

import com.steambeat.test.FakeInternet;
import org.apache.commons.io.IOUtils;
import org.junit.*;

import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.notNullValue;

public class TestsSteambeatSitemapModuleLink {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Test
    @Ignore("connection refused on CI")
    public void canGetSitemap() throws Exception {
        final SteambeatSitemapModuleLink steambeatSitemapModuleLink = new SteambeatSitemapModuleLink();

        final InputStream stream = steambeatSitemapModuleLink.get("00001");

        assertThat(stream, notNullValue());
        final String sitemap = IOUtils.toString(stream);
        assertThat(sitemap, is("index : 00001"));
    }
}
