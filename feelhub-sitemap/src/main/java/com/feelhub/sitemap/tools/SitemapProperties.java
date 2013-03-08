package com.feelhub.sitemap.tools;

import org.mongolink.Settings;
import org.mongolink.domain.UpdateStrategies;

import java.io.IOException;
import java.util.Properties;

public class SitemapProperties {

    public SitemapProperties() {
        properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sitemap.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties", e);
        }
    }

    private String getDbHost() {
        return properties.getProperty("dbHost");
    }

    private int getDbPort() {
        return Integer.valueOf(properties.getProperty("dbPort"));
    }

    private String getDbName() {
        return properties.getProperty("dbName");
    }

    public Settings getDbSettings() {
        return Settings.defaultInstance()
                .withHost(getDbHost())
                .withPort(getDbPort())
                .withDbName(getDbName()).withDefaultUpdateStrategy(UpdateStrategies.DIFF);
    }

    public String getRoot() {
        return properties.getProperty("root");
    }

    private final Properties properties;
}
