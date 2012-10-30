package com.feelhub.web.resources.authentification;

import com.feelhub.web.*;
import org.junit.*;
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
        final FacebookConnector connector = new FacebookConnector("appId", "secret");

        final String url = connector.getUrl();

        assertThat(url).isEqualTo("https://www.facebook.com/dialog/oauth?client_id=appId&redirect_uri=https%3A%2F%2Fthedomain%2F%2Ffacebooklogin");
    }
}
