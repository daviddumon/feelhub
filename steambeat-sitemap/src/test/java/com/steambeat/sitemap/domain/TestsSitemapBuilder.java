//package com.steambeat.sitemap.domain;
//
//import com.google.common.collect.Lists;
//import com.steambeat.sitemap.tools.XmlTransformer;
//import org.apache.commons.io.FileUtils;
//import org.joda.time.*;
//import org.junit.*;
//import org.w3c.dom.Document;
//
//import java.io.*;
//import java.util.*;
//
//import static org.hamcrest.Matchers.*;
//import static org.junit.Assert.*;
//
//@SuppressWarnings("unchecked")
//public class TestsSitemapBuilder {
//
//    @After
//    public void tearDown() {
//        try {
//            FileUtils.deleteDirectory(directory);
//            FileUtils.deleteQuietly(new File("hiram.log"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void hasACurrentSitemap() {
//        SitemapBuilder sitemapBuilder = new SitemapBuilder();
//
//        sitemapBuilder.build(0, Lists.newArrayList());
//
//        assertThat(sitemapBuilder.getCurrentSitemap(), notNullValue());
//    }
//
//    @Test
//    public void hasACurrentSitemapIndex() {
//        SitemapBuilder sitemapBuilder = new SitemapBuilder();
//
//        sitemapBuilder.build(0, Lists.newArrayList());
//
//        assertThat(sitemapBuilder.getCurrentSitemapIndex(), notNullValue());
//    }
//
//    @Test
//    public void canCalculateNewIndex() {
//        SitemapBuilder sitemapBuilder = new SitemapBuilder();
//
//        sitemapBuilder.build(0, Lists.newArrayList());
//
//        assertThat("sitemap_index_00001.xml.gz", is(sitemapBuilder.getCurrentSitemapIndex().getName()));
//        assertThat("sitemap_00001.xml.gz", is(sitemapBuilder.getCurrentSitemap().getFileName()));
//    }
//
//    @Test
//    public void canCalculateGoodIndex() {
//        SitemapBuilder sitemapBuilder = new SitemapBuilder();
//
//        sitemapBuilder.build(100001, Lists.newArrayList());
//
//        assertThat("sitemap_index_00001.xml.gz", is(sitemapBuilder.getCurrentSitemapIndex().getName()));
//        assertThat("sitemap_00003.xml.gz", is(sitemapBuilder.getCurrentSitemap().getFileName()));
//    }
//
//    @Test
//    public void canAddUrisToCurrentSitemap() {
//        XmlTransformer xmlTransformer = new XmlTransformer();
//        SitemapBuilder sitemapBuilder = new SitemapBuilder();
//        List uris = getUris();
//
//        sitemapBuilder.build(0, uris);
//
//        File file = new File(directoryName, sitemapBuilder.getCurrentSitemap().getFileName());
//        Document document = xmlTransformer.readFromFile(file);
//        assertThat(document.getElementsByTagName("url").getLength(), is(1));
//        assertThat(document.getElementsByTagName("loc").item(0).getTextContent(), is("http://www.test.com"));
//        assertThat(document.getElementsByTagName("changefreq").item(0).getTextContent(), is(Frequence.hourly.toString()));
//        assertThat(document.getElementsByTagName("priority").item(0).getTextContent(), is(String.valueOf(0.5)));
//    }
//
//    @Test
//    public void canAddSitemapToSitemapIndex() {
//        XmlTransformer xmlTransformer = new XmlTransformer();
//        SitemapBuilder sitemapBuilder = new SitemapBuilder();
//        List uris = getUris();
//
//        sitemapBuilder.build(0, uris);
//
//        File file = new File(directoryName, sitemapBuilder.getCurrentSitemapIndex().getName());
//        Document document = xmlTransformer.readFromFile(file);
//        assertThat(document.getElementsByTagName("sitemap").getLength(), is(1));
//        assertThat(document.getElementsByTagName("loc").item(0).getTextContent(), is("http://www.steambeat.com/sitemap_00001.xml.gz"));
//    }
//
//    @Test
//    public void canChangeSitemapIndexWhen50001Uris() {
//        SitemapBuilder sitemapBuilder = new SitemapBuilder();
//        sitemapBuilder.build(0, Lists.newArrayList());
//        Sitemap sitemap = sitemapBuilder.getCurrentSitemap();
//
//        List uris = Lists.newArrayList();
//        for (int i = 1; i <= 50001; i++) {
//            Map uri = new HashMap();
//            uri.put("value", "fake" + i);
//            uri.put("frequence", Frequence.hourly);
//            uri.put("priority", 0.5);
//            uris.add(uri);
//        }
//        sitemapBuilder.build(0, uris);
//
//        Sitemap currentitemap = sitemapBuilder.getCurrentSitemap();
//        SitemapIndex currentSitemapIndex = sitemapBuilder.getCurrentSitemapIndex();
//        assertThat(sitemap, notNullValue());
//        assertThat(currentitemap, notNullValue());
//        assertThat(sitemap.getFileName(), not(currentitemap.getFileName()));
//        assertThat(currentitemap.getCount(), is(1));
//        assertThat(currentSitemapIndex.getCount(), is(2));
//    }
//
//    @Test
//    public void canChangeLastModDateInIndexWhenAddingUris() {
//        DateTimeUtils.setCurrentMillisSystem();
//        XmlTransformer xmlTransformer = new XmlTransformer();
//        SitemapBuilder sitemapBuilder = new SitemapBuilder();
//        List uris = getUris();
//        sitemapBuilder.build(0, uris);
//        File file = new File(directoryName, sitemapBuilder.getCurrentSitemapIndex().getName());
//        Document document = xmlTransformer.readFromFile(file);
//        DateTime oldDate = new DateTime(document.getElementsByTagName("lastmod").item(0).getTextContent());
//
//        sitemapBuilder.build(1, uris);
//
//        document = xmlTransformer.readFromFile(file);
//        DateTime newDate = new DateTime(document.getElementsByTagName("lastmod").item(0).getTextContent());
//        assertThat(oldDate, not(newDate));
//    }
//
//    private List getUris() {
//        List uris = Lists.newArrayList();
//        Map uri = new HashMap();
//        uri.put("value", "http://www.test.com");
//        uri.put("frequence", Frequence.hourly);
//        uri.put("priority", 0.5);
//        uris.add(uri);
//        return uris;
//    }
//
//    private String directoryName = "/hiram/sitemaps";
//    private File directory = new File(directoryName);
//}
