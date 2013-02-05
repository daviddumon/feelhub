package com.feelhub.tools;

import org.mongolink.Settings;
import org.mongolink.domain.UpdateStrategies;

import java.io.IOException;
import java.util.Properties;

public class FeelhubApplicationProperties {

    public FeelhubApplicationProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/feelhub-application.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties", e);
        }
    }

    public Settings getDbSettings() {
        return Settings.defaultInstance()
                .withHost(getDbHost())
                .withPort(getDbPort())
                .withDbName(getDbName())
                .withDefaultUpdateStrategy(UpdateStrategies.DIFF);
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

    public String getAlchemyApiKey() {
        return properties.getProperty("alchemyApiKey");
    }

    public String getBingApiKey() {
        return properties.getProperty("bingApiKey");
    }

    public String getBingRoot() {
        return properties.getProperty("bingRoot");
    }

    public String getCloudinaryName() {
        return properties.getProperty("cloudinaryName");
    }

    public String getCloudinaryApiKey() {
        return properties.getProperty("cloudinaryApiKey");
    }

    public String getCloudinaryApiSecret() {
        return properties.getProperty("cloudinaryApiSecret");
    }

    private final Properties properties;
}
