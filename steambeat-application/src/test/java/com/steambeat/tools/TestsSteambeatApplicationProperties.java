package com.steambeat.tools;

import org.junit.Test;
import org.mongolink.Settings;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

public class TestsSteambeatApplicationProperties {

    @Test
    public void canGetDbSettings() {
        final SteambeatApplicationProperties applicationProperties = new SteambeatApplicationProperties();

        final Settings settings = applicationProperties.getDbSettings();

        assertThat(settings.getDbName(), is("steambeat"));
    }
}
