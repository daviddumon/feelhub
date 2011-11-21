package com.steambeat.sitemap.domain;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

public class SitemapBuilder {

    public void build(final int urisLastCount, final List uris) {
        initialize(urisLastCount);
        if (currentSitemap.getCount() == 0 && uris.size() > 0) {
            currentSitemapIndex.add(currentSitemap);
        }
        Iterator iterator = uris.iterator();
        while (iterator.hasNext()) {
            HashMap uri = (HashMap) iterator.next();
            currentSitemap.add(uri.get("value").toString(), (Frequence) uri.get("frequence"), (Double) uri.get("priority"));
            if (currentSitemap.getCount() == numberOfUrisPerSitemap) {
                changeCurrentSitemap();
            }
        }
        commitChanges();
    }

    private void initialize(final int urisLastCount) {
        initializeDirectory();
        initializeSitemapIndex(urisLastCount);
        initializeSitemap(urisLastCount);
    }

    private void initializeDirectory() {
        String directoryName = "/hiram/sitemaps";
        directory = new File(directoryName);
        try {
            if (!directory.exists()) {
                FileUtils.forceMkdir(directory);
                logger.info("creation of directory : " + directoryName);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void initializeSitemapIndex(final int urisLastCount) {
        Double fileIndex = Math.floor(urisLastCount / (numberOfSitemapsPerIndex * numberOfUrisPerSitemap)) + 1;
        currentSitemapIndex = new SitemapIndex(directory, fileIndex.intValue());
    }

    private void initializeSitemap(final int urisLastCount) {
        Double fileIndex = Math.ceil(urisLastCount / numberOfUrisPerSitemap) + 1;
        currentSitemap = new Sitemap(directory, fileIndex.intValue());
    }

    private void changeCurrentSitemap() {
        currentSitemap.writeToFile();
        currentSitemap = new Sitemap(directory, currentSitemap.getIndex() + 1);
        currentSitemapIndex.add(currentSitemap);
    }

    private void commitChanges() {
        currentSitemap.writeToFile();
        currentSitemapIndex.mod(currentSitemap);
        currentSitemapIndex.writeToFile();
    }

    public SitemapIndex getCurrentSitemapIndex() {
        return currentSitemapIndex;
    }

    public Sitemap getCurrentSitemap() {
        return currentSitemap;
    }

    private File directory;
    private Sitemap currentSitemap;
    private SitemapIndex currentSitemapIndex;
    private static final int numberOfUrisPerSitemap = 50000;
    private static final int numberOfSitemapsPerIndex = 50000;
    private static final Logger logger = Logger.getLogger(SitemapBuilder.class);
}
