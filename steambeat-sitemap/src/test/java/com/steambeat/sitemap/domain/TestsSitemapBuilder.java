package com.steambeat.sitemap.domain;

import com.google.common.collect.Lists;
import com.steambeat.sitemap.domain.sitemap.*;
import org.junit.*;
import org.w3c.dom.Document;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@SuppressWarnings("unchecked")
public class TestsSitemapBuilder {

    @Before
    public void setUp() {
        sitemapBuilder = new SitemapBuilder();
    }

    @Test
    public void hasACurrentSitemapIndex() {
        assertThat(sitemapBuilder.getCurrentSitemapIndex(), notNullValue());
        assertThat(sitemapBuilder.getCurrentSitemapIndex(), is(SitemapIndex.class));
        assertThat(sitemapBuilder.getCurrentSitemapIndex().getPath(), is("http://www.steambeat.com/sitemap_index_00001.xml"));
    }

    @Test
    public void canAddSitemapEntryToCurrentSitemap() {

        sitemapBuilder.build(getSingleEntry());

        final Document xml = sitemapBuilder.getCurrentSitemapIndex().getLastSitemap().getXMLRepresentation();
        assertThat(xml.getElementsByTagName("url").getLength(), is(1));
        assertThat(xml.getElementsByTagName("loc").item(0).getTextContent(), is("http://www.fakeentry.com"));
        assertThat(xml.getElementsByTagName("changefreq").item(0).getTextContent(), is(Frequency.hourly.toString()));
        assertThat(xml.getElementsByTagName("priority").item(0).getTextContent(), is(String.valueOf(0.5)));
    }

    @Test
    public void addAFirstSitemapToSitemapIndex() {
        final SitemapIndex sitemapIndex = sitemapBuilder.getCurrentSitemapIndex();
        final Sitemap sitemap = sitemapBuilder.getCurrentSitemapIndex().getLastSitemap();

        assertNotNull(sitemapIndex);
        assertNotNull(sitemap);
        assertThat(sitemap.getPath(), is("http://www.steambeat.com/sitemap_00001.xml"));
        assertThat(sitemapIndex.getXMLRepresentation().getElementsByTagName("sitemap").getLength(), is(1));
        assertThat(sitemapIndex.getXMLRepresentation().getElementsByTagName("loc").item(0).getTextContent(), is("http://www.steambeat.com/sitemap_00001.xml"));
    }

    @Test
    public void canAddALotOfSitemapEntriesToSitemapBuilder() {

        sitemapBuilder.build(getEntries());

        final SitemapIndex sitemapIndex = sitemapBuilder.getCurrentSitemapIndex();
        assertThat(sitemapIndex.getXMLRepresentation().getElementsByTagName("sitemap").getLength(), is(2));
    }

    @Test
    public void canChangeCurrentSitemapIndexOnCapacityException() {
    }

    private List<SitemapEntry> getSingleEntry() {
        final List<SitemapEntry> sitemapEntries = Lists.newArrayList();
        sitemapEntries.add(new SitemapEntry("http://www.fakeentry.com", Frequency.hourly, 0.5));
        return sitemapEntries;
    }

    public List<SitemapEntry> getEntries() {
        final List<SitemapEntry> sitemapEntries = Lists.newArrayList();
        for (int i = 0; i < 55000; i++) {
            sitemapEntries.add(new SitemapEntry("http://www.fakeentry.com" + String.valueOf(i), Frequency.hourly, 0.5));
        }
        return sitemapEntries;
    }

    private SitemapBuilder sitemapBuilder;
}
