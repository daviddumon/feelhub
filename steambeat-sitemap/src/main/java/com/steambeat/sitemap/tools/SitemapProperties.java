package com.steambeat.sitemap.tools;

import org.mongolink.Settings;

import java.io.IOException;
import java.util.Properties;

public class SitemapProperties {

    public SitemapProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/sitemap.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties", e);
        }
    }

    public String getDbHost() {
        return properties.getProperty("dbHost");
    }

    public int getDbPort() {
        return Integer.valueOf(properties.getProperty("dbPort"));
    }

    public String getDbName() {
        return properties.getProperty("dbName");
    }

    public Settings getDbSettings() {
        return Settings.defaultInstance()
                .withHost(getDbHost())
                .withPort(getDbPort())
                .withDbName(getDbName());
    }

    public int getDelay() {
        return Integer.valueOf(properties.getProperty("delay"));
    }

    private final Properties properties;
}
