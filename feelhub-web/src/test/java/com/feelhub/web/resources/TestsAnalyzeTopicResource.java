package com.feelhub.web.resources;

import com.feelhub.web.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.fest.assertions.Assertions.*;

public class TestsAnalyzeTopicResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void checkCredentials() {
        final ClientResource analyzeResource = restlet.newClientResource("/analyze?q=http://www.lemonde.fr");

        analyzeResource.get();

        assertThat(analyzeResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_UNAUTHORIZED);
    }
}
