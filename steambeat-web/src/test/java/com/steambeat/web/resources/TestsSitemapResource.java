package com.steambeat.web.resources;

import com.steambeat.tools.Hiram;
import com.steambeat.web.*;
import org.apache.commons.io.IOUtils;
import org.junit.*;
import org.restlet.data.*;
import org.restlet.representation.Representation;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

public class TestsSitemapResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void setUp() throws Exception {
        hiram = mock(Hiram.class);
        restlet.setHiram(hiram);
    }

    @Test
    public void isMapped() {
        final ClientResource resource = restlet.newClientResource("/sitemap_00001.xml.gz");

        final Representation representation = resource.get();

        assertThat(resource.getStatus(), is(Status.SUCCESS_OK));
        assertThat(representation.getMediaType(), is(MediaType.TEXT_XML));
    }

    @Test
    public void doesUseHiram() throws IOException {
        when(hiram.getSitemap("00001")).thenReturn(IOUtils.toInputStream("your mother"));
        final ClientResource resource = restlet.newClientResource("/sitemap_00001.xml.gz");

        final Representation representation = resource.get();

        verify(hiram).getSitemap("00001");
        assertThat(representation.getText(), is("your mother"));
    }

    private Hiram hiram;
}
