package com.feelhub.tools;

import com.google.common.collect.Lists;
import com.mongodb.ServerAddress;
import org.mongolink.Settings;
import org.mongolink.domain.UpdateStrategies;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
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
        return Settings.defaultInstance().withAddresses(getServerAddresses())
                .withDbName(getDbName())
                .withDefaultUpdateStrategy(UpdateStrategies.DIFF);
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
