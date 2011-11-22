package com.steambeat.sitemap.domain;

import com.google.common.collect.Lists;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.util.List;

public class SitemapIndex {

    public SitemapIndex(final int index) {
        name = "sitemap_index_" + String.format("%05d", index) + ".xml";
    }

    public void add(final Sitemap sitemap) {
        if (sitemaps.size() >= capacity) {
            throw new SitemapIndexCapacityException();
        }
        sitemaps.add(sitemap);
    }

    public Document getXMLRepresentation() {
        Document xml = null;
        try {
            xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            appendHead(xml);
            appendSitemaps(xml);
        } catch (ParserConfigurationException e) {

        }
        return xml;
    }

    private void appendHead(Document xml) {
        xml.setXmlVersion("1.0");
        Element head = xml.createElement("sitemapindex");
        head.setAttribute("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9");
        head.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        head.setAttribute("xsi:schemaLocation", "http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/siteindex.xsd");
        xml.appendChild(head);
        xml.setXmlStandalone(true);
    }

    private void appendSitemaps(final Document xml) {
        for (Sitemap sitemap : sitemaps) {
            appendSitemap(xml, sitemap);
        }
    }

    private void appendSitemap(Document xml, Sitemap sitemap) {
        Element sitemapTag = xml.createElement("sitemap");
        Element locTag = xml.createElement("loc");
        Element lastmodTag = xml.createElement("lastmod");
        locTag.setTextContent(sitemap.getPath());
        lastmodTag.setTextContent(sitemap.getLastModTime().toString());
        sitemapTag.appendChild(locTag);
        sitemapTag.appendChild(lastmodTag);
        xml.getElementsByTagName("sitemapindex").item(0).appendChild(sitemapTag);
    }

    public String getPath() {
        return "http://www.steambeat.com/" + name;
    }

    public List<Sitemap> getSitemaps() {
        return sitemaps;
    }

    public Sitemap getLastSitemap() {
        return sitemaps.get(sitemaps.size() - 1);
    }

    private String name;
    private List<Sitemap> sitemaps = Lists.newArrayList();
    private int capacity = 50000;
}
