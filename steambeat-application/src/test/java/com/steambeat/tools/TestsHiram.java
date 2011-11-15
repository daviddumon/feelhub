package com.steambeat.tools;

import com.steambeat.test.FakeInternet;
import org.apache.commons.io.IOUtils;
import org.junit.*;

import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.notNullValue;

public class TestsHiram {

    @BeforeClass
    public static void beforeClass() throws Exception {
        fakeInternet = new FakeInternet();
    }

    @AfterClass
    public static void afterClass() {
        fakeInternet.stop();
    }

    @Test
    public void canGetSitemap() throws Exception {
        final Hiram hiram = new Hiram();

        final InputStream stream = hiram.getSitemap("00001");

        assertThat(stream, notNullValue());
        final String sitemap = IOUtils.toString(stream);
        assertThat(sitemap, is("index : 00001"));
    }

    private static FakeInternet fakeInternet;
}
