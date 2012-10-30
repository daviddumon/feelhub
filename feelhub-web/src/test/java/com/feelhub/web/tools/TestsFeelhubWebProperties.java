package com.feelhub.web.tools;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsFeelhubWebProperties {

    @Test
    public void canGetSitemapBuilderAddress() {
        final FeelhubWebProperties feelhubWebProperties = new FeelhubWebProperties();

        final String address = feelhubWebProperties.getSitemapBuilderAddress();

        assertThat(address, is("http://localhost:6162"));
    }

    @Test
    public void canGetBuildTimeParameter() {
        final FeelhubWebProperties feelhubWebProperties = new FeelhubWebProperties();

        final String buildTime = feelhubWebProperties.getBuildTime();

        assertThat(buildTime, is("buildtime"));
    }

    @Test
    public void canGetReadyParameter() {
        final FeelhubWebProperties feelhubWebProperties = new FeelhubWebProperties();

        final String buildTime = feelhubWebProperties.getReadyState();

        assertThat(buildTime, is("true"));
    }

    @Test
    public void canGetStatusParameter() {
        final FeelhubWebProperties feelhubWebProperties = new FeelhubWebProperties();

        final String status = feelhubWebProperties.getStatus();

        assertThat(status, is("start"));
    }

    @Test
    public void canGetCookieDomainParameter() {
        final FeelhubWebProperties feelhubWebProperties = new FeelhubWebProperties();

        final String status = feelhubWebProperties.getCookie();

        assertThat(status, is(".test.localhost"));
    }

    @Test
    public void canGetSecureModeParameter() {
        final FeelhubWebProperties feelhubWebProperties = new FeelhubWebProperties();

        final String status = feelhubWebProperties.getSecureMode();

        assertThat(status, is("false"));
    }

    @Test
    public void canGetCookieBaseTime() {
        final FeelhubWebProperties feelhubWebProperties = new FeelhubWebProperties();

        final String cookieBaseTime = feelhubWebProperties.getCookieBaseTime();

        assertThat(cookieBaseTime, is("1"));
    }

    @Test
    public void canGetCookiePermanentTime() {
        final FeelhubWebProperties feelhubWebProperties = new FeelhubWebProperties();

        final String cookiePermanentTime = feelhubWebProperties.getCookiePermanentTime();

        assertThat(cookiePermanentTime, is("10"));
    }

    @Test
    public void canGetSessionBaseTime() {
        final FeelhubWebProperties feelhubWebProperties = new FeelhubWebProperties();

        final String sessionBaseTime = feelhubWebProperties.getSessionBaseTime();

        assertThat(sessionBaseTime, is("5"));
    }

    @Test
    public void canGetSessionPermanentTime() {
        final FeelhubWebProperties feelhubWebProperties = new FeelhubWebProperties();

        final String sessionPermanentTime = feelhubWebProperties.getSessionPermanentTime();

        assertThat(sessionPermanentTime, is("10"));
    }
}
