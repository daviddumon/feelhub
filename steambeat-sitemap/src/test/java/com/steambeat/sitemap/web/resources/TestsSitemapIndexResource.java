package com.steambeat.sitemap.web.resources;

import com.steambeat.sitemap.domain.*;
import com.steambeat.sitemap.test.WithRobotFile;
import com.steambeat.sitemap.web.*;
import com.steambeat.sitemap.web.representation.SitemapTemplateRepresentation;
import org.junit.*;
import org.restlet.data.Status;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsSitemapIndexResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithRobotFile robots = new WithRobotFile();

    @Test
    public void isMapped() {
        final ClientResource sitemapIndexResource = restlet.newClientResource("/sitemap_index_00001.xml");

        sitemapIndexResource.get();

        assertThat(sitemapIndexResource.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void hasSitemapIndexes() {
        final SitemapIndex sitemapIndex = RobotsFile.INSTANCE.newSitemapIndex();
        sitemapIndex.newSitemap();
        sitemapIndex.newSitemap();
        final ClientResource sitemapIndexResource = restlet.newClientResource("/sitemap_index_00001.xml");

        final SitemapTemplateRepresentation representation = (SitemapTemplateRepresentation) sitemapIndexResource.get();

        assertThat(sitemapIndexResource.getStatus(), is(Status.SUCCESS_OK));
        final Map<String,Object> dataModel = representation.getDataModel();
        final List<Sitemap> sitemaps = (List<Sitemap>) dataModel.get("sitemaps");
        assertThat(sitemaps.size(), is(2));
    }
}
