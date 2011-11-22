package com.steambeat.sitemap.domain;

import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.util.List;

public class Sitemap {

    public Sitemap(final int index) {
        name = "sitemap_" + String.format("%05d", index) + ".xml";
    }

    public void addEntry(final SitemapEntry sitemapEntry) {
        if (sitemapEntries.size() >= capacity) {
            throw new SitemapCapacityException();
        }
        sitemapEntries.add(sitemapEntry);
    }

    public List<SitemapEntry> getEntries() {
        return sitemapEntries;
    }
    
    public Document getXMLRepresentation() {
        Document xml = null;
        try {
            xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            appendHead(xml);
            appendEntries(xml);
        } catch (ParserConfigurationException e) {

        }
        return xml;
    }

    private void appendHead(Document xml) {
        xml.setXmlVersion("1.0");
        Element root = xml.createElement("urlset");
        root.setAttribute("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9");
        root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        root.setAttribute("xsi:schemaLocation", "http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd");
        xml.appendChild(root);
        xml.setXmlStandalone(true);
    }

    private void appendEntries(final Document xml) {
        for (SitemapEntry sitemapEntry : sitemapEntries) {
            appendEntry(xml, sitemapEntry);
        }
    }

    private void appendEntry(final Document xml, SitemapEntry sitemapEntry) {
        Element urlTag = xml.createElement("url");
        Element locTag = xml.createElement("loc");
        Element lastmodTag = xml.createElement("lastmod");
        Element changefreqTag = xml.createElement("changefreq");
        Element priorityTag = xml.createElement("priority");
        locTag.setTextContent(sitemapEntry.getLoc());
        lastmodTag.setTextContent(sitemapEntry.getLastMod().toString());
        changefreqTag.setTextContent(sitemapEntry.getFrequency().toString());
        priorityTag.setTextContent(String.valueOf(sitemapEntry.getPriority()));
        urlTag.appendChild(locTag);
        urlTag.appendChild(lastmodTag);
        urlTag.appendChild(changefreqTag);
        urlTag.appendChild(priorityTag);
        xml.getElementsByTagName("urlset").item(0).appendChild(urlTag);
    }

    public String getPath() {
        return "http://www.steambeat.com/" + name;
    }

    public DateTime getLastModTime() {
        return new DateTime();
    }

    private String name;
    private List<SitemapEntry> sitemapEntries = Lists.newArrayList();
    private final int capacity = 50000;
}
