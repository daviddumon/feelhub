package com.steambeat.sitemap.web.resources;

import com.steambeat.sitemap.domain.*;
import com.steambeat.sitemap.test.WithRobotFile;
import com.steambeat.sitemap.web.*;
import com.steambeat.sitemap.web.representation.SitemapTemplateRepresentation;
import org.junit.*;
import org.restlet.data.*;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsRobotsResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithRobotFile withRobotFile = new WithRobotFile();

    @Test
    public void isMapped() throws IOException {
        final ClientResource robotsFile = restlet.newClientResource("/robots.txt");

        final SitemapTemplateRepresentation representation = (SitemapTemplateRepresentation) robotsFile.get();

        assertThat(robotsFile.getStatus(), is(Status.SUCCESS_OK));
        assertThat(representation.getMediaType(), is(MediaType.TEXT_PLAIN));
        assertThat(representation.getText(), containsString("Disallow"));
    }

    @Test
    public void hasGoodDataModel() {
        RobotsFile.INSTANCE.newSitemapIndex();
        RobotsFile.INSTANCE.newSitemapIndex();
        RobotsFile.INSTANCE.newSitemapIndex();
        final ClientResource robotsFile = restlet.newClientResource("/robots.txt");

        final SitemapTemplateRepresentation representation = (SitemapTemplateRepresentation) robotsFile.get();

        assertThat(robotsFile.getStatus(), is(Status.SUCCESS_OK));
        final List<SitemapIndex> indexes = (List<SitemapIndex>) representation.getDataModel().get("indexes");
        assertThat(indexes.size(), is(3));
    }
}
