package com.feelhub.web.social;

import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.tools.FeelhubWebProperties;
import org.junit.Test;
import org.restlet.Context;

import static org.fest.assertions.Assertions.assertThat;

public class GoogleConnectorTest {

    @Test
    public void canFormUri() {
        Context.setCurrent(ContextTestFactory.buildContext());
        FeelhubWebProperties properties = properties();
        GoogleConnector connector = new GoogleConnector(properties);

        String url = connector.getUrl();

        assertThat(url).contains("https://accounts.google.com/o/oauth2/auth");
        assertThat(url).contains("appId");
    }

    private FeelhubWebProperties properties() {
        FeelhubWebProperties properties = new FeelhubWebProperties();
        properties.googleAppId ="appId";
        properties.googleAppSecret="secret";
        return properties;
    }
}
