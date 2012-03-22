package com.steambeat.sitemap.domain;

import com.google.common.collect.Lists;
import com.steambeat.sitemap.domain.sitemap.Sitemap;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.util.List;

public class SitemapIndex {

    public SitemapIndex(final int index) {
        name = "sitemap_index_" + String.format("%05d", index) + ".xml";
    }

    public void add(final Sitemap sitemap) {
        final int capacity = 50000;
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

    private void appendHead(final Document xml) {
        xml.setXmlVersion("1.0");
        final Element head = xml.createElement("sitemapindex");
        head.setAttribute("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9");
        head.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        head.setAttribute("xsi:schemaLocation", "http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/siteindex.xsd");
        xml.appendChild(head);
        xml.setXmlStandalone(true);
    }

    private void appendSitemaps(final Document xml) {
        for (final Sitemap sitemap : sitemaps) {
            appendSitemap(xml, sitemap);
        }
    }

    private void appendSitemap(final Document xml, final Sitemap sitemap) {
        final Element sitemapTag = xml.createElement("sitemap");
        final Element locTag = xml.createElement("loc");
        final Element lastmodTag = xml.createElement("lastmod");
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

    private final String name;
    private final List<Sitemap> sitemaps = Lists.newArrayList();
}
