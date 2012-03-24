package com.steambeat.web.resources;

import com.steambeat.tools.SteambeatSitemapModuleLink;
import com.steambeat.web.*;
import org.apache.commons.io.IOUtils;
import org.junit.*;
import org.restlet.data.*;
import org.restlet.representation.Representation;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

public class TestsSteambeatRobotsResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void setUp() throws Exception {
        steambeatSitemapModuleLink = mock(SteambeatSitemapModuleLink.class);
        restlet.setSitemapLink(steambeatSitemapModuleLink);
    }

    @Test
    public void isMapped() {
        final ClientResource resource = restlet.newClientResource("/robots.txt");

        final Representation representation = resource.get();

        assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
        assertThat(representation.getMediaType(), is(MediaType.TEXT_XML));
    }

    @Test
    public void doesUseSitemapLink() throws IOException {
        when(steambeatSitemapModuleLink.get("/robots.txt")).thenReturn(IOUtils.toInputStream("robots"));
        final ClientResource resource = restlet.newClientResource("/robots.txt");

        final Representation representation = resource.get();

        verify(steambeatSitemapModuleLink).get("/robots.txt");
        assertThat(representation.getText(), is("robots"));
    }

    private SteambeatSitemapModuleLink steambeatSitemapModuleLink;
}
