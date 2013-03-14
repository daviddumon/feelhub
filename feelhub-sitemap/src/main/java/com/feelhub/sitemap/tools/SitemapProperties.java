package com.feelhub.sitemap.tools;

import com.feelhub.tools.FeelhubConfigurationException;
import com.google.common.collect.Lists;
import com.mongodb.ServerAddress;
import org.mongolink.Settings;
import org.mongolink.domain.UpdateStrategies;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
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

    private List<ServerAddress> getServerAddresses() {
        return parseAddresses(properties.getProperty("dbAddresses"));
    }

    private List<ServerAddress> parseAddresses(String dbAddresses) {
        List<ServerAddress> adresses = Lists.newArrayList();
        for (String address : dbAddresses.split(",")) {
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
                .withDbName(getDbName()).withDefaultUpdateStrategy(UpdateStrategies.DIFF);
    }

    public String getRoot() {
        return properties.getProperty("root");
    }

    private final Properties properties;
}
