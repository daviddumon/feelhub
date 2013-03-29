package com.feelhub.web.social;

import com.feelhub.web.*;
import com.feelhub.web.tools.FeelhubWebProperties;
import org.junit.*;
import org.restlet.Context;

import static org.fest.assertions.Assertions.*;

public class FacebookConnectorTest {

    @After
    public void tearDown() throws Exception {
        Context.setCurrent(null);
    }

    @Test
    public void canGenerateAuthUri() {
        Context.setCurrent(ContextTestFactory.buildContext());
        final FeelhubWebProperties properties = new FeelhubWebProperties();
        properties.facebookAppId = "appId";
        properties.facebookAppSecret = "appSecret";
        properties.domain = "https://thedomain";
        final FacebookConnector connector = new FacebookConnector(properties);

        final String url = connector.getUrl();

        assertThat(url).isEqualTo("https://www.facebook.com/dialog/oauth?client_id=appId&redirect_uri=https%3A%2F%2Fthedomain%2F%2Fsocial%2Ffacebook&scope=email,publish_actions");
    }
}
