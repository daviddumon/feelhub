package com.steambeat.tools;

import org.junit.Test;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

public class TestsSteambeatWebProperties {

    @Test
    public void canGetHiramAddress() {
        final SteambeatWebProperties steambeatWebProperties = new SteambeatWebProperties();

        final String address = steambeatWebProperties.getHiramAddress();

        assertThat(address, is("http://localhost:6162/hiram"));
    }

    @Test
    public void canGetBuildTimeParameter() {
        final SteambeatWebProperties steambeatWebProperties = new SteambeatWebProperties();

        final String buildTime = steambeatWebProperties.getBuildTime();

        assertThat(buildTime, is("buildtime"));
    }
}
