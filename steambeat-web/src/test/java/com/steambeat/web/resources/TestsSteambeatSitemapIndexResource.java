package com.steambeat.web.resources;

import com.steambeat.web.*;
import com.steambeat.web.tools.SteambeatSitemapModuleLink;
import org.apache.commons.io.IOUtils;
import org.junit.*;
import org.restlet.data.*;
import org.restlet.representation.Representation;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class TestsSteambeatSitemapIndexResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void setUp() throws Exception {
        steambeatSitemapModuleLink = mock(SteambeatSitemapModuleLink.class);
        restlet.setSitemapLink(steambeatSitemapModuleLink);
    }

    @Test
    public void steambeatSitemapIndexResourceIsMapped() {
        final ClientResource resource = restlet.newClientResource("/sitemap_index_00001.xml");

        final Representation representation = resource.get();

        assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
        assertThat(representation.getMediaType(), is(MediaType.TEXT_XML));
    }

    @Test
    public void doesUseSitemapLink() throws IOException {
        when(steambeatSitemapModuleLink.get("/sitemap_index_00001.xml")).thenReturn(IOUtils.toInputStream("sitemap"));
        final ClientResource resource = restlet.newClientResource("/sitemap_index_00001.xml");

        final Representation representation = resource.get();

        verify(steambeatSitemapModuleLink).get("/sitemap_index_00001.xml");
        assertThat(representation.getText(), is("sitemap"));
    }

    private SteambeatSitemapModuleLink steambeatSitemapModuleLink;
}
