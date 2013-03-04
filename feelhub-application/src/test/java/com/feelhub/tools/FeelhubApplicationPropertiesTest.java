package com.feelhub.tools;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class FeelhubApplicationPropertiesTest {

    @Test
    public void canGetProperties() {
        final FeelhubApplicationProperties properties = new FeelhubApplicationProperties();

        assertThat(properties.getDbSettings().getDbName(), is("feelhub"));
        assertThat(properties.getAlchemyApiKey(), is("testapikey"));
        assertThat(properties.getBingApiKey(), is("testbingapikey"));
        assertThat(properties.getBingRoot(), is("http://localhost:6162/bing?query='"));
    }
}
