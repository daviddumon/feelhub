package com.steambeat.web.tools;

import org.junit.Test;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

public class TestsSteambeatWebProperties {

    @Test
    public void canGetSitemapBuilderAddress() {
        final SteambeatWebProperties steambeatWebProperties = new SteambeatWebProperties();

        final String address = steambeatWebProperties.getSitemapBuilderAddress();

        assertThat(address, is("http://localhost:6162"));
    }

    @Test
    public void canGetBuildTimeParameter() {
        final SteambeatWebProperties steambeatWebProperties = new SteambeatWebProperties();

        final String buildTime = steambeatWebProperties.getBuildTime();

        assertThat(buildTime, is("buildtime"));
    }

    @Test
    public void canGetReadyParameter() {
        final SteambeatWebProperties steambeatWebProperties = new SteambeatWebProperties();

        final String buildTime = steambeatWebProperties.getReadyState();

        assertThat(buildTime, is("true"));
    }

    @Test
    public void canGetStatusParameter() {
        final SteambeatWebProperties steambeatWebProperties = new SteambeatWebProperties();

        final String status = steambeatWebProperties.getStatus();

        assertThat(status, is("start"));
    }
}
