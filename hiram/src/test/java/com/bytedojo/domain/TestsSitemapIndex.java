package com.bytedojo.domain;

import com.bytedojo.test.SystemTime;
import com.bytedojo.tools.XmlTransformer;
import org.apache.commons.io.FileUtils;
import org.joda.time.*;
import org.junit.*;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import javax.xml.transform.TransformerException;
import java.io.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsSitemapIndex {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void setUp() throws IOException {
        directory = new File("sitemaps");
        FileUtils.forceMkdir(directory);
        sitemapIndex = new SitemapIndex(directory, 1);
    }

    @After
    public void tearDown() {
        try {
            FileUtils.deleteDirectory(directory);
            FileUtils.deleteQuietly(new File("hiram.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void canAddSitemapToSitemapIndex() {
        Sitemap sitemap = new Sitemap(directory, 1);

        sitemapIndex.add(sitemap);

        assertThat(sitemapIndex.getCount(), is(1));
    }

    @Test
    public void canWriteXMLHead() {
        XmlTransformer xmlTransformer = new XmlTransformer();

        sitemapIndex.writeToFile();

        File file = new File(directory, "sitemap_index_00001.xml.gz");
        Document document = xmlTransformer.readFromFile(file);
        assertThat(document.getFirstChild().getNodeName(), is("sitemapindex"));
        assertThat(document.getXmlVersion(), is("1.0"));
    }

    @Test
    public void canAddASitemapToIndex() {
        Sitemap sitemap = new Sitemap(new File("sitemaps"), 2);
        XmlTransformer xmlTransformer = new XmlTransformer();

        sitemapIndex.add(sitemap);
        sitemapIndex.writeToFile();

        File file = new File(directory, "sitemap_index_00001.xml.gz");
        Document document = xmlTransformer.readFromFile(file);
        assertThat(document.getElementsByTagName("sitemapindex").item(0).getFirstChild().getNodeName(), is("sitemap"));
        assertThat(document.getElementsByTagName("sitemap").getLength(), is(1));
        assertThat(document.getElementsByTagName("sitemap").item(0).getChildNodes().item(0).getNodeName(), is("loc"));
        assertThat(document.getElementsByTagName("sitemap").item(0).getChildNodes().item(1).getNodeName(), is("lastmod"));
        assertThat(document.getElementsByTagName("loc").item(0).getTextContent(), is(sitemap.getPath()));
        assertThat(document.getElementsByTagName("lastmod").item(0).getTextContent(), is(time.getNow().toString()));
    }

    @Test
    public void canGetPath() {
        assertThat("http://www.kikiyoo.com/sitemap_index_00001.xml.gz", is(sitemapIndex.getPath()));
    }

    @Test
    public void canLoadExistingSitemap() {
        XmlTransformer xmlTransformer = new XmlTransformer();
        Sitemap sitemap = new Sitemap(new File("sitemaps"), 1);
        sitemapIndex.add(sitemap);
        sitemapIndex.writeToFile();

        SitemapIndex sitemapIndexForTest = new SitemapIndex(directory, 1);
        sitemapIndexForTest.add(sitemap);
        sitemapIndexForTest.writeToFile();

        File file = new File(directory, sitemapIndex.getFileName());
        Document document = xmlTransformer.readFromFile(file);
        assertThat(document.getElementsByTagName("sitemap").getLength(), is(2));
    }

    @Test
    public void canCountWhenLoadingXmlFile() throws TransformerException, ParserConfigurationException {
        makeFakeXmlDocument();

        SitemapIndex sitemapIndexForTest = new SitemapIndex(directory, 1);

        assertThat(sitemapIndexForTest.getCount(), is(1));
    }

    @Test
    public void canModifyLastModDate() {
        XmlTransformer xmlTransformer = new XmlTransformer();
        DateTimeUtils.setCurrentMillisSystem();
        Sitemap sitemap = new Sitemap(new File("sitemaps"), 1);
        sitemapIndex.add(sitemap);
        sitemapIndex.writeToFile();
        File file = new File(directory, sitemapIndex.getFileName());
        Document document = xmlTransformer.readFromFile(file);
        DateTime oldDate = new DateTime(document.getElementsByTagName("lastmod").item(0).getTextContent());

        sitemapIndex.mod(sitemap);
        sitemapIndex.writeToFile();

        document = xmlTransformer.readFromFile(file);
        DateTime newDate = new DateTime(document.getElementsByTagName("lastmod").item(0).getTextContent());
        assertThat(oldDate, not(newDate));
    }

    private void makeFakeXmlDocument() throws ParserConfigurationException, TransformerException {
        Document xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element urlsetTag = xml.createElement("sitemapindex");
        Element urlTag = xml.createElement("sitemap");
        Element locTag = xml.createElement("loc");
        Element lastmodTag = xml.createElement("lastmod");
        lastmodTag.setTextContent(new DateTime().toString());
        locTag.setTextContent("sitemap_00001.xml");
        urlTag.appendChild(locTag);
        urlTag.appendChild(lastmodTag);
        urlsetTag.appendChild(urlTag);
        xml.appendChild(urlsetTag);
        XmlTransformer xmlTransformer = new XmlTransformer();
        xmlTransformer.writeToFile(new File(directory, sitemapIndex.getFileName()), xml);
    }

    private SitemapIndex sitemapIndex;
    private File directory;
}
