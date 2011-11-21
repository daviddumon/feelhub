package com.steambeat.sitemap.tools;

import com.steambeat.sitemap.domain.Frequence;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.File;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsXmlTransformer {

    @After
    public void tearDown() {
        FileUtils.deleteQuietly(new File("test.xml.gz"));
    }

    @Test
    public void canWriteXMLToGZIPFile() {
        XmlTransformer xmlTransformer = new XmlTransformer();
        Document xml = getXMLDocument();
        File file = new File("test.xml.gz");

        xmlTransformer.writeToFile(file, xml);

        File newFile = new File("test.xml.gz");
        assertTrue(newFile.exists());
    }

    @Test
    public void canReadXMLFromGZIPFile() {
        XmlTransformer xmlTransformer = new XmlTransformer();
        Document xml = getXMLDocument();
        File file = new File("test.xml.gz");
        xmlTransformer.writeToFile(file, xml);

        Document newXml = xmlTransformer.readFromFile(file);

        assertThat(newXml.toString(), is(xml.toString()));
    }

    private Document getXMLDocument() {
        try {
            Document xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element urlsetTag = xml.createElement("urlset");
            Element urlTag = xml.createElement("url");
            Element locTag = xml.createElement("loc");
            Element changefreqTag = xml.createElement("changefreq");
            locTag.setTextContent("http://www.xmlgzip.com");
            changefreqTag.setTextContent(Frequence.hourly.toString());
            urlTag.appendChild(locTag);
            urlTag.appendChild(changefreqTag);
            urlsetTag.appendChild(urlTag);
            xml.appendChild(urlsetTag);
            return xml;
        } catch (ParserConfigurationException e) {
        }
        return null;
    }
}
