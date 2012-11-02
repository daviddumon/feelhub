package com.feelhub.web.authentification;

import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.WebApplicationTester;
import com.feelhub.web.tools.FeelhubWebProperties;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.restlet.Context;

import static org.fest.assertions.Assertions.*;

public class TestsFacebookConnector {

    @Rule
    public WebApplicationTester webApp = new WebApplicationTester();

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

        assertThat(url).isEqualTo("https://www.facebook.com/dialog/oauth?client_id=appId&redirect_uri=https%3A%2F%2Fthedomain%2F%2Fsocial%2Ffacebook");
    }
}
