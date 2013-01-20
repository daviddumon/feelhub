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
                .withDefaultUpdateStrategy(UpdateStrategies.OVERWRITE);
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

    public String getBingRoot() {
        return properties.getProperty("bingRoot");
    }

    private final Properties properties;
}
