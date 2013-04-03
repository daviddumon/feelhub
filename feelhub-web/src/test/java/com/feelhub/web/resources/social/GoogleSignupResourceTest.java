package com.feelhub.web.resources.social;

import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.social.GoogleConnector;
import org.junit.Test;
import org.restlet.data.*;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class GoogleSignupResourceTest {

    @Test
    public void canRedirectToGoogleAuth() {
        final GoogleConnector connector = mock(GoogleConnector.class);
        when(connector.getUrl()).thenReturn("http://test");
        final GoogleSignupResource resource = new GoogleSignupResource(connector);
        ContextTestFactory.initResource(resource);

        resource.redirect();

        assertThat(resource.getStatus()).isEqualTo(Status.REDIRECTION_TEMPORARY);
        assertThat(resource.getLocationRef()).isEqualTo(new Reference("http://test"));
    }
}
