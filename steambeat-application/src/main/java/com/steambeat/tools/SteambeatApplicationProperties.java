package com.steambeat.tools;


import org.mongolink.Settings;
import org.mongolink.domain.UpdateStrategies;

import java.io.IOException;
import java.util.Properties;

public class SteambeatApplicationProperties {

    public SteambeatApplicationProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/steambeat-application.properties"));
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

    private final Properties properties;
}
