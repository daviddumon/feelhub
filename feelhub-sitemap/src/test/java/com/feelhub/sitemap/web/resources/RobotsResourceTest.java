package com.feelhub.sitemap.web.resources;

import com.feelhub.sitemap.domain.SitemapIndex;
import com.feelhub.sitemap.test.WithFakeData;
import com.feelhub.sitemap.web.*;
import com.feelhub.sitemap.web.representation.SitemapTemplateRepresentation;
import org.junit.*;
import org.restlet.data.Status;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class RobotsResourceTest {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeData withFakeData = new WithFakeData();

    @Test
    public void isMapped() {
        final ClientResource robots = restlet.newClientResource("/robots.txt");

        robots.get();

        assertThat(robots.getStatus(), is(Status.SUCCESS_OK));
    }

    @Test
    public void hasAllSitemapIndexesInDataModel() {
        final ClientResource robots = restlet.newClientResource("/robots.txt");

        final SitemapTemplateRepresentation representation = (SitemapTemplateRepresentation) robots.get();

        final Map<String, Object> dataModel = representation.getDataModel();
        final List<SitemapIndex> indexes = (List<SitemapIndex>) dataModel.get("indexes");
        assertThat(indexes, notNullValue());
        assertThat(indexes.size(), is(2));
    }
}