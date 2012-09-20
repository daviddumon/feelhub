package com.steambeat.web.tools;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

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

    @Test
    public void canGetCookieDomainParameter() {
        final SteambeatWebProperties steambeatWebProperties = new SteambeatWebProperties();

        final String status = steambeatWebProperties.getCookie();

        assertThat(status, is(".test.localhost"));
    }

    @Test
    public void canGetSecureModeParameter() {
        final SteambeatWebProperties steambeatWebProperties = new SteambeatWebProperties();

        final String status = steambeatWebProperties.getSecureMode();

        assertThat(status, is("false"));
    }

    @Test
    public void canGetCookieBaseTime() {
        final SteambeatWebProperties steambeatWebProperties = new SteambeatWebProperties();

        final String cookieBaseTime = steambeatWebProperties.getCookieBaseTime();

        assertThat(cookieBaseTime, is("1"));
    }

    @Test
    public void canGetCookiePermanentTime() {
        final SteambeatWebProperties steambeatWebProperties = new SteambeatWebProperties();

        final String cookiePermanentTime = steambeatWebProperties.getCookiePermanentTime();

        assertThat(cookiePermanentTime, is("10"));
    }

    @Test
    public void canGetSessionBaseTime() {
        final SteambeatWebProperties steambeatWebProperties = new SteambeatWebProperties();

        final String sessionBaseTime = steambeatWebProperties.getSessionBaseTime();

        assertThat(sessionBaseTime, is("5"));
    }

    @Test
    public void canGetSessionPermanentTime() {
        final SteambeatWebProperties steambeatWebProperties = new SteambeatWebProperties();

        final String sessionPermanentTime = steambeatWebProperties.getSessionPermanentTime();

        assertThat(sessionPermanentTime, is("10"));
    }
}
