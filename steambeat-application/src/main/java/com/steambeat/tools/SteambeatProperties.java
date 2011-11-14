package com.steambeat.tools;

import fr.bodysplash.mongolink.Settings;
import fr.bodysplash.mongolink.domain.UpdateStrategies;

import java.io.IOException;
import java.util.Properties;

public class SteambeatProperties {

    public SteambeatProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/steambeat.properties"));
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
                .withDbName(getDbName())
                .withDefaultUpdateStrategy(UpdateStrategies.DIFF);
    }

    public boolean isDev() {
        return Boolean.valueOf(properties.getProperty("dev"));
    }

    public String getDomain() {
        return properties.getProperty("domain");
    }

    public String getHiramAddress() {
        return properties.getProperty("hiram");
    }

    private Properties properties;
}
