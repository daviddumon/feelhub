package com.steambeat.web.tools;

import java.io.IOException;
import java.util.Properties;

public class SteambeatWebProperties {

    public SteambeatWebProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/steambeat-web.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties", e);
        }
    }

    public boolean isDev() {
        return Boolean.valueOf(properties.getProperty("dev"));
    }

    public String getDomain() {
        return properties.getProperty("domain");
    }

    public String getSitemapBuilderAddress() {
        return properties.getProperty("sitemapBuilder");
    }

    public String getBuildTime() {
        return properties.getProperty("buildtime");
    }

    public String getReadyState() {
        return properties.getProperty("ready");
    }

    private final Properties properties;
}