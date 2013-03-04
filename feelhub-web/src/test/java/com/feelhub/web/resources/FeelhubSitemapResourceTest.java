package com.feelhub.web.resources;

import com.feelhub.web.*;
import com.feelhub.web.tools.FeelhubSitemapModuleLink;
import org.apache.commons.io.IOUtils;
import org.junit.*;
import org.restlet.data.*;
import org.restlet.representation.Representation;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class FeelhubSitemapResourceTest {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void setUp() throws Exception {
        feelhubSitemapModuleLink = mock(FeelhubSitemapModuleLink.class);
        restlet.setSitemapLink(feelhubSitemapModuleLink);
    }

    @Test
    public void feelhubSitemapResourceIsMapped() {
        final ClientResource resource = restlet.newClientResource("/sitemap_00001.xml");

        final Representation representation = resource.get();

        assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
        assertThat(representation.getMediaType(), is(MediaType.TEXT_XML));
    }

    @Test
    public void doesUseSitemapLink() throws IOException {
        when(feelhubSitemapModuleLink.get("/sitemap_00001.xml")).thenReturn(IOUtils.toInputStream("sitemap"));
        final ClientResource resource = restlet.newClientResource("/sitemap_00001.xml");

        final Representation representation = resource.get();

        verify(feelhubSitemapModuleLink).get("/sitemap_00001.xml");
        assertThat(representation.getText(), is("sitemap"));
    }

    private FeelhubSitemapModuleLink feelhubSitemapModuleLink;
}
