package com.steambeat.sitemap.domain;

import com.steambeat.sitemap.tools.XmlTransformer;
import org.joda.time.DateTime;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;

public class SitemapIndex {

    public SitemapIndex(final File directory, final int index) {
        this.index = index;
        file = new File(directory, "sitemap_index_" + String.format("%05d", index) + ".xml.gz");
        if (file.exists()) {
            loadSitemapIndex();
        } else {
            initializeSitemapIndex();
        }
    }

    private void loadSitemapIndex() {
        XmlTransformer xmlTransformer = new XmlTransformer();
        xml = xmlTransformer.readFromFile(file);
        count = xml.getFirstChild().getChildNodes().getLength();
    }

    private void initializeSitemapIndex() {
        try {
            file.createNewFile();
        } catch (IOException e) {
        }
        createXMLHead();
        count = 0;
    }

    private void createXMLHead() {
        try {
            xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            xml.setXmlVersion("1.0");
            Element root = xml.createElement("sitemapindex");
            root.setAttribute("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9");
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            root.setAttribute("xsi:schemaLocation", "http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/siteindex.xsd");
            xml.appendChild(root);
            xml.setXmlStandalone(true);
        } catch (ParserConfigurationException e) {
        }
    }

    public void writeToFile() {
        XmlTransformer xmlTransformer = new XmlTransformer();
        xmlTransformer.writeToFile(file, xml);
    }

    public void add(final Sitemap sitemap) {
        Element sitemapTag = xml.createElement("sitemap");
        Element locTag = xml.createElement("loc");
        Element lastmodTag = xml.createElement("lastmod");
        locTag.setTextContent(sitemap.getPath());
        lastmodTag.setTextContent(new DateTime().toString());
        sitemapTag.appendChild(locTag);
        sitemapTag.appendChild(lastmodTag);
        xml.getElementsByTagName("sitemapindex").item(0).appendChild(sitemapTag);
        count++;
    }

    public void mod(final Sitemap sitemap) {
        NodeList sitemaps = xml.getFirstChild().getChildNodes();
        for (int i = 0; i < sitemaps.getLength(); i++) {
            Node item = sitemaps.item(i);
            if (item.getFirstChild().getTextContent().equals(sitemap.getPath())) {
                item.getChildNodes().item(1).setTextContent(new DateTime().toString());
            }
        }
    }

    public int getCount() {
        return count;
    }

    public String getPath() {
        return "http://www.steambeat.com/" + file.getName();
    }

    public String getFileName() {
        return file.getName();
    }

    public int getIndex() {
        return index;
    }


    private int index;
    private File file;
    private Document xml;
    private int count;
}
