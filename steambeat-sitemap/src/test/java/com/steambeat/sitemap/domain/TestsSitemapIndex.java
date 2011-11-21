package com.steambeat.sitemap.domain;

import com.steambeat.test.SystemTime;
import org.junit.*;
import org.w3c.dom.Document;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsSitemapIndex {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void setUp() {
        sitemapIndex = new SitemapIndex(1);
    }

    @Test
    public void canAddSitemapToSitemapIndex() {
        Sitemap sitemap = new Sitemap(1);

        sitemapIndex.add(sitemap);

        assertThat(sitemapIndex.getSitemaps().size(), is(1));
        assertThat(sitemapIndex.getSitemaps().get(0), is(sitemap));
    }

    @Test
    public void canGetXMLRepresentationOfSitemapIndex() {
        Sitemap sitemap = new Sitemap(2);

        sitemapIndex.add(sitemap);
        Document document = sitemapIndex.getXMLRepresentation();

        assertThat(document.getFirstChild().getNodeName(), is("sitemapindex"));
        assertThat(document.getXmlVersion(), is("1.0"));
        assertThat(document.getElementsByTagName("sitemapindex").item(0).getFirstChild().getNodeName(), is("sitemap"));
        assertThat(document.getElementsByTagName("sitemap").getLength(), is(1));
        assertThat(document.getElementsByTagName("sitemap").item(0).getChildNodes().item(0).getNodeName(), is("loc"));
        assertThat(document.getElementsByTagName("sitemap").item(0).getChildNodes().item(1).getNodeName(), is("lastmod"));
        assertThat(document.getElementsByTagName("loc").item(0).getTextContent(), is(sitemap.getPath()));
        assertThat(document.getElementsByTagName("lastmod").item(0).getTextContent(), is(time.getNow().toString()));
    }

    @Test
    public void canGetPath() {
        assertThat(sitemapIndex.getPath(), is("http://www.steambeat.com/sitemap_index_00001.xml"));
    }

    private SitemapIndex sitemapIndex;
}
