package com.feelhub.web.social;

import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.tools.FeelhubWebProperties;
import org.junit.Test;
import org.restlet.Context;

import static org.fest.assertions.Assertions.*;

public class GoogleConnectorTest {

    @Test
    public void canFormUri() {
        Context.setCurrent(ContextTestFactory.buildContext());
        final FeelhubWebProperties properties = properties();
        final GoogleConnector connector = new GoogleConnector(properties);

        final String url = connector.getUrl();

        assertThat(url).contains("https://accounts.google.com/o/oauth2/auth");
        assertThat(url).contains("appId");
    }

    private FeelhubWebProperties properties() {
        final FeelhubWebProperties properties = new FeelhubWebProperties();
        properties.googleAppId = "appId";
        properties.googleAppSecret = "secret";
        return properties;
    }
}
