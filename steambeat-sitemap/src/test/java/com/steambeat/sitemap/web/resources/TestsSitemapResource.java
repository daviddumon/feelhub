package com.steambeat.sitemap.web.resources;

import com.steambeat.sitemap.domain.*;
import com.steambeat.sitemap.test.WithFakeData;
import com.steambeat.sitemap.web.*;
import com.steambeat.sitemap.web.representation.SitemapTemplateRepresentation;
import org.junit.*;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSitemapResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeData entries = new WithFakeData();

    @Test
    public void isMapped() {
        final ClientResource sitemapResource = restlet.newClientResource("/sitemap_00001.xml");

        sitemapResource.get();

        assertThat(sitemapResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void hasEntriesInDataModel() {
        final ClientResource sitemapResource = restlet.newClientResource("/sitemap_00001.xml");

        final SitemapTemplateRepresentation representation = (SitemapTemplateRepresentation) sitemapResource.get();

        assertThat(sitemapResource.getStatus(), is(Status.SUCCESS_OK));
        final Map<String, Object> dataModel = representation.getDataModel();
        final List<SitemapEntry> entries = (List<SitemapEntry>) dataModel.get("entries");
        assertThat(entries.size(), is(Sitemap.getCapacity()));
    }

    @Test
    public void error404ForUnknownSitemaps() {
        final ClientResource sitemapResource = restlet.newClientResource("/sitemap_00011.xml");

        final Representation representation = sitemapResource.get();

        assertThat(sitemapResource.getStatus(), is(Status.CLIENT_ERROR_NOT_FOUND));
    }
}
