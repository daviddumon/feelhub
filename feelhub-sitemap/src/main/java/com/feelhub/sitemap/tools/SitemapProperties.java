package com.feelhub.sitemap.tools;

import com.feelhub.tools.FeelhubConfigurationException;
import com.google.common.collect.Lists;
import com.mongodb.*;
import org.mongolink.Settings;
import org.mongolink.domain.UpdateStrategies;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

public class SitemapProperties {

    public SitemapProperties() {
        properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sitemap.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties", e);
        }
    }

    private List<ServerAddress> getServerAddresses() {
        return parseAddresses(properties.getProperty("dbAddresses"));
    }

    private List<ServerAddress> parseAddresses(final String dbAddresses) {
        final List<ServerAddress> adresses = Lists.newArrayList();
        for (final String address : dbAddresses.split(",")) {
            try {
                adresses.add(new ServerAddress(address));
            } catch (UnknownHostException e) {
                throw new FeelhubConfigurationException(e);
            }
        }
        return adresses;
    }

    private String getDbName() {
        return properties.getProperty("dbName");
    }

    public Settings getDbSettings() {
        return Settings.defaultInstance().withAddresses(getServerAddresses())
                .withDbName(getDbName()).withDefaultUpdateStrategy(UpdateStrategies.DIFF).withReadPreference(ReadPreference.secondary());
    }

    public String getRoot() {
        return properties.getProperty("root");
    }

    private final Properties properties;
}
