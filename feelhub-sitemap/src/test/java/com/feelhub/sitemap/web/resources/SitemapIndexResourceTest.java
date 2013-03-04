package com.feelhub.sitemap.web.resources;

import com.feelhub.sitemap.domain.Sitemap;
import com.feelhub.sitemap.test.WithFakeData;
import com.feelhub.sitemap.web.*;
import com.feelhub.sitemap.web.representation.SitemapTemplateRepresentation;
import org.junit.*;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class SitemapIndexResourceTest {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeData entries = new WithFakeData();

    @Test
    public void isMapped() {
        final ClientResource sitemapIndexResource = restlet.newClientResource("/sitemap_index_00001.xml");

        sitemapIndexResource.get();

        assertThat(sitemapIndexResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void hasSitemapIndexes() {
        final ClientResource sitemapIndexResource = restlet.newClientResource("/sitemap_index_00001.xml");

        final SitemapTemplateRepresentation representation = (SitemapTemplateRepresentation) sitemapIndexResource.get();

        assertThat(sitemapIndexResource.getStatus(), is(Status.SUCCESS_OK));
        final Map<String, Object> dataModel = representation.getDataModel();
        final List<Sitemap> sitemaps = (List<Sitemap>) dataModel.get("sitemaps");
        assertThat(sitemaps.size(), is(2));
    }

    @Test
    public void error404onUnknownSitemapIndex() {
        final ClientResource sitemapIndexResource = restlet.newClientResource("/sitemap_index_00010.xml");

        final Representation representation = sitemapIndexResource.get();

        assertThat(sitemapIndexResource.getStatus(), is(Status.CLIENT_ERROR_NOT_FOUND));
    }
}
