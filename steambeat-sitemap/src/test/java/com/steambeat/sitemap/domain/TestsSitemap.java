package com.steambeat.sitemap.domain;

import com.steambeat.sitemap.domain.sitemap.*;
import com.steambeat.test.SystemTime;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.w3c.dom.Document;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsSitemap {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void setUp() {
        sitemap = new Sitemap(1);
    }

    @Test
    public void canGetPath() {
        assertThat(sitemap.getPath(), is("http://www.steambeat.com/sitemap_" + String.format("%05d", 1) + ".xml"));
    }

    @Test
    public void canAddASitemapEntryToSitemap() {
        final String uri = "http://www.fakeentry.com";
        final SitemapEntry sitemapEntry = new SitemapEntry(uri, Frequency.hourly, 0.5);

        sitemap.addEntry(sitemapEntry);

        assertThat(sitemap.getEntries().size(), is(1));
        final Document xml = sitemap.getXMLRepresentation();
        assertNotNull(xml);
        assertThat(xml.getFirstChild().getNodeName(), is("urlset"));
        assertThat(xml.getXmlVersion(), is("1.0"));
        assertThat(xml.getElementsByTagName("urlset").item(0).getFirstChild().getNodeName(), is("url"));
        assertThat(xml.getElementsByTagName("url").getLength(), is(1));
        assertThat(xml.getElementsByTagName("url").item(0).getChildNodes().item(0).getNodeName(), is("loc"));
        assertThat(xml.getElementsByTagName("url").item(0).getChildNodes().item(1).getNodeName(), is("lastmod"));
        assertThat(xml.getElementsByTagName("url").item(0).getChildNodes().item(2).getNodeName(), is("changefreq"));
        assertThat(xml.getElementsByTagName("url").item(0).getChildNodes().item(3).getNodeName(), is("priority"));
        assertThat(xml.getElementsByTagName("loc").item(0).getTextContent(), is(uri));
        assertThat(xml.getElementsByTagName("lastmod").item(0).getTextContent(), is(time.getNow().toString()));
        assertThat(xml.getElementsByTagName("changefreq").item(0).getTextContent(), is("hourly"));
        assertThat(xml.getElementsByTagName("priority").item(0).getTextContent(), is(String.valueOf(0.5)));
    }

    @Test
    public void canThrowSitemapCapacityException() {
        exception.expect(SitemapCapacityException.class);

        for (int i = 0; i < 50001; i++) {
            sitemap.addEntry(new SitemapEntry(String.valueOf(i), Frequency.hourly, 0.5));
        }
    }

    private Sitemap sitemap;
}
