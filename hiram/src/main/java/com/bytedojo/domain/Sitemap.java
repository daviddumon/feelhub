package com.bytedojo.domain;

import com.bytedojo.tools.XmlTransformer;
import org.apache.log4j.Logger;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;

public class Sitemap {

    public Sitemap(final File directory, final int index) {
        this.index = index;
        file = new File(directory, "sitemap_" + String.format("%05d", index) + ".xml.gz");
        if (file.exists()) {
            loadSitemap();
            logger.info(file.getName() + " loaded");
        } else {
            initializeSitemap();
            logger.info(file.getName() + " has been created in directory " + directory.getName());
        }
    }

    public Sitemap() {

    }

    public Sitemap(final String name) {
        this.name = name;
    }

    private void loadSitemap() {
        XmlTransformer xmlTransformer = new XmlTransformer();
        xml = xmlTransformer.readFromFile(file);
        count = xml.getFirstChild().getChildNodes().getLength();
    }

    private void initializeSitemap() {
        try {
            file.createNewFile();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        createXMLHead();
        count = 0;
    }

    private void createXMLHead() {
        try {
            xml = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            xml.setXmlVersion("1.0");
            Element root = xml.createElement("urlset");
            root.setAttribute("xmlns", "http://www.sitemaps.org/schemas/sitemap/0.9");
            root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            root.setAttribute("xsi:schemaLocation", "http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd");
            xml.appendChild(root);
            xml.setXmlStandalone(true);
        } catch (ParserConfigurationException e) {
            logger.error(e.getMessage());
        }
    }

    public void writeToFile() {
        XmlTransformer xmlTransformer = new XmlTransformer();
        xmlTransformer.writeToFile(file, xml);
    }

    public void add(final String uri, final Frequence frequence, final double priority) {
        Element urlTag = xml.createElement("url");
        Element locTag = xml.createElement("loc");
        Element changefreqTag = xml.createElement("changefreq");
        Element priorityTag = xml.createElement("priority");
        locTag.setTextContent(uri);
        changefreqTag.setTextContent(frequence.toString());
        priorityTag.setTextContent(String.valueOf(priority));
        urlTag.appendChild(locTag);
        urlTag.appendChild(changefreqTag);
        urlTag.appendChild(priorityTag);
        xml.getElementsByTagName("urlset").item(0).appendChild(urlTag);
        logger.info("append uri \"" + uri + "\" to file " + file.getName());
        count++;
    }

    public String getPath() {
        return "http://www.kikiyoo.com/" + file.getName();
    }

    public String getFileName() {
        return file.getName();
    }

    public int getCount() {
        return count;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    private int index;
    private File file;
    private Document xml;
    private int count;
    private static final Logger logger = Logger.getLogger(Sitemap.class);
    private String name;
}
