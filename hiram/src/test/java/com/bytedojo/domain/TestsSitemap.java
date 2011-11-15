package com.bytedojo.domain;

import com.bytedojo.tools.XmlTransformer;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.TransformerException;
import java.io.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsSitemap {

    @Before
    public void setUp() throws IOException {
        directory = new File("/hiram/sitemaps");
        FileUtils.forceMkdir(directory);
        sitemapIndex = 1;
        sitemap = new Sitemap(directory, sitemapIndex);
    }

    @After
    public void tearDown() {
        try {
            FileUtils.deleteDirectory(directory);
            //FileUtils.deleteQuietly(new File("hiram.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void canCreateASitemap() throws IOException {
        File file = new File(directory, "sitemap_" + String.format("%05d", sitemapIndex) + ".xml.gz");

        assertTrue(file.exists());
        assertTrue(file.isFile());
        assertTrue(file.canRead());
        assertTrue(file.canWrite());
        assertFalse(file.isHidden());
    }

    @Test
    public void canGetKikiyooPath() {
        String path = sitemap.getPath();

        assertThat("http://www.steambeat.com/sitemap_" + String.format("%05d", sitemapIndex) + ".xml.gz", is(path));
    }

    @Test
    public void canWriteXMLHeadToFile() {
        XmlTransformer xmlTransformer = new XmlTransformer();

        sitemap.writeToFile();

        File file = new File(directory, sitemap.getFileName());
        Document document = xmlTransformer.readFromFile(file);
        assertThat(document.getFirstChild().getNodeName(), is("urlset"));
        assertThat(document.getXmlVersion(), is("1.0"));
    }

    @Test
    public void canAddAPageToSitemap() {
        XmlTransformer xmlTransformer = new XmlTransformer();
        String uri = "http://www.steambeat.com/test";

        sitemap.add(uri, Frequence.hourly, 0.5);
        sitemap.writeToFile();

        File file = new File(directory, sitemap.getFileName());
        Document document = xmlTransformer.readFromFile(file);
        assertThat(document.getElementsByTagName("urlset").item(0).getFirstChild().getNodeName(), is("url"));
        assertThat(document.getElementsByTagName("url").getLength(), is(1));
        assertThat(document.getElementsByTagName("url").item(0).getChildNodes().item(0).getNodeName(), is("loc"));
        assertThat(document.getElementsByTagName("url").item(0).getChildNodes().item(1).getNodeName(), is("changefreq"));
        assertThat(document.getElementsByTagName("url").item(0).getChildNodes().item(2).getNodeName(), is("priority"));
        assertThat(document.getElementsByTagName("loc").item(0).getTextContent(), is(uri));
        assertThat(document.getElementsByTagName("changefreq").item(0).getTextContent(), is("hourly"));
        assertThat(document.getElementsByTagName("priority").item(0).getTextContent(), is(String.valueOf(0.5)));
    }

    @Test
    public void canLoadExistingSitemap() {
        XmlTransformer xmlTransformer = new XmlTransformer();
        String uri = "http://www.steambeat.com/test";
        sitemap.add(uri, Frequence.hourly, 0.5);
        sitemap.writeToFile();

        Sitemap sitemapForTest = new Sitemap(directory, 1);
        sitemapForTest.add(uri, Frequence.hourly, 0.5);
        sitemapForTest.writeToFile();

        File file = new File(directory, sitemapForTest.getFileName());
        Document document = xmlTransformer.readFromFile(file);
        assertThat(document.getElementsByTagName("url").getLength(), is(2));
    }

    @Test
    public void canCount() {
        assertThat(sitemap.getCount(), is(0));

        sitemap.add("http://www.test.com", Frequence.hourly, 0.5);
        assertThat(sitemap.getCount(), is(1));
    }

    @Test
    public void canCountWhenLoading() throws ParserConfigurationException, TransformerException {
        makeFakeXmlDocument();

        Sitemap sitemapForTest = new Sitemap(directory, 1);

        assertThat(sitemapForTest.getCount(), is(1));
    }

    private void makeFakeXmlDocument() throws ParserConfigurationException, TransformerException {
        Document xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element urlsetTag = xml.createElement("urlset");
        Element urlTag = xml.createElement("url");
        Element locTag = xml.createElement("loc");
        Element changefreqTag = xml.createElement("changefreq");
        locTag.setTextContent("http://www.test.com");
        changefreqTag.setTextContent(Frequence.hourly.toString());
        urlTag.appendChild(locTag);
        urlTag.appendChild(changefreqTag);
        urlsetTag.appendChild(urlTag);
        xml.appendChild(urlsetTag);
        XmlTransformer xmlTransformer = new XmlTransformer();
        xmlTransformer.writeToFile(new File(directory, sitemap.getFileName()), xml);
    }

    private int sitemapIndex;
    private File directory;
    private Sitemap sitemap;
}
