package com.feelhub.tools;

import org.junit.Test;
import org.mongolink.Settings;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsFeelhubApplicationProperties {

    @Test
    public void canGetDbSettings() {
        final FeelhubApplicationProperties applicationProperties = new FeelhubApplicationProperties();

        final Settings settings = applicationProperties.getDbSettings();

        assertThat(settings.getDbName(), is("feelhub"));
    }

    @Test
    public void canGetAlchemyApiKey() {
        final FeelhubApplicationProperties applicationProperties = new FeelhubApplicationProperties();

        final String alchemyApiKey = applicationProperties.getAlchemyApiKey();

        assertThat(alchemyApiKey, is("testapikey"));
    }

    @Test
    public void canGetBingRoot() {
        final FeelhubApplicationProperties applicationProperties = new FeelhubApplicationProperties();

        final String alchemyApiKey = applicationProperties.getBingRoot();

        assertThat(alchemyApiKey, is("http://localhost:6162/bing?query='"));
    }
}
