package com.steambeat.sitemap.web.resources;

import com.steambeat.sitemap.web.*;
import com.steambeat.sitemap.web.representation.SitemapTemplateRepresentation;
import org.junit.*;
import org.restlet.data.*;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsRobotsResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Test
    public void isMapped() throws IOException {
        final ClientResource robotsFile = restlet.newClientResource("/robots.txt");

        final SitemapTemplateRepresentation representation = (SitemapTemplateRepresentation) robotsFile.get();

        assertThat(robotsFile.getStatus(), is(Status.SUCCESS_OK));
        assertThat(representation.getMediaType(), is(MediaType.TEXT_PLAIN));
        assertThat(representation.getText(), containsString("Disallow"));
    }
}
