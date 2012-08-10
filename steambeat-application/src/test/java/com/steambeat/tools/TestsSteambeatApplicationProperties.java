package com.steambeat.tools;

import org.junit.Test;
import org.mongolink.Settings;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class TestsSteambeatApplicationProperties {

    @Test
    public void canGetDbSettings() {
        final SteambeatApplicationProperties applicationProperties = new SteambeatApplicationProperties();

        final Settings settings = applicationProperties.getDbSettings();

        assertThat(settings.getDbName(), is("steambeat"));
    }

    @Test
    public void canGetAlchemyApiKey() {
        final SteambeatApplicationProperties applicationProperties = new SteambeatApplicationProperties();

        final String alchemyApiKey = applicationProperties.getAlchemyApiKey();

        assertThat(alchemyApiKey, is("testapikey"));
    }
}
