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

@Ignore("non understandable compression on tomcat ...")
public class FeelhubRobotsResourceTest {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void setUp() throws Exception {
        feelhubSitemapModuleLink = mock(FeelhubSitemapModuleLink.class);
        restlet.setSitemapLink(feelhubSitemapModuleLink);
    }

    @Test
    public void feelhubRobotsResourceIsMapped() {
        final ClientResource resource = restlet.newClientResource("/robots.txt");

        final Representation representation = resource.get();

        assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
        assertThat(representation.getMediaType(), is(MediaType.TEXT_PLAIN));
    }

    @Test
    public void doesUseSitemapLink() throws IOException {
        when(feelhubSitemapModuleLink.get("/robots.txt")).thenReturn(IOUtils.toInputStream("robots"));
        final ClientResource resource = restlet.newClientResource("/robots.txt");

        final Representation representation = resource.get();

        verify(feelhubSitemapModuleLink).get("/robots.txt");
        assertThat(representation.getText(), is("robots"));
    }

    private FeelhubSitemapModuleLink feelhubSitemapModuleLink;
}
