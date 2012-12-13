package com.feelhub.web.tools;

import com.feelhub.web.guice.GuiceTestModule;
import com.google.inject.*;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsFeelhubWebProperties {

    @Before
    public void setUp() throws Exception {
        final Injector injector = Guice.createInjector(new GuiceTestModule());
        feelhubWebProperties = injector.getInstance(FeelhubWebProperties.class);
    }

    @Test
    public void canGetSitemapBuilderAddress() {

        final String address = feelhubWebProperties.sitemapBuilder;

        assertThat(address, is("http://localhost:6162"));
    }

    @Test
    public void canGetBuildTimeParameter() {
        final String buildTime = feelhubWebProperties.buildtime;

        assertThat(buildTime, is("buildtime"));
    }

    @Test
    public void canGetReadyParameter() {
        final String buildTime = feelhubWebProperties.ready;

        assertThat(buildTime, is("true"));
    }

    @Test
    public void canGetStatusParameter() {
        final String status = feelhubWebProperties.status;

        assertThat(status, is("start"));
    }

    @Test
    public void canGetCookieDomainParameter() {
        final String status = feelhubWebProperties.cookie;

        assertThat(status, is(".test.localhost"));
    }

    @Test
    public void canGetSecureModeParameter() {
        final boolean status = feelhubWebProperties.secureMode;

        assertThat(status, is(false));
    }

    @Test
    public void canGetCookieBaseTime() {
        final int cookieBaseTime = feelhubWebProperties.cookieBaseTime;

        assertThat(cookieBaseTime, is(1));
    }

    @Test
    public void canGetCookiePermanentTime() {
        final int cookiePermanentTime = feelhubWebProperties.cookiePermanentTime;

        assertThat(cookiePermanentTime, is(10));
    }

    @Test
    public void canGetSessionBaseTime() {
        final int sessionBaseTime = feelhubWebProperties.sessionbasetime;

        assertThat(sessionBaseTime, is(5));
    }

    @Test
    public void canGetSessionPermanentTime() {
        final int sessionPermanentTime = feelhubWebProperties.sessionPermanentTime;

        assertThat(sessionPermanentTime, is(10));
    }

    private FeelhubWebProperties feelhubWebProperties;
}
