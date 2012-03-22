package com.steambeat.sitemap.domain;

import com.steambeat.sitemap.test.WithRobotFile;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsRobotsFile {

    @Rule
    public WithRobotFile withRobotFile = new WithRobotFile();

    @Test
    public void canCreateASitemapIndex() {
        SitemapIndex sitemapIndex = RobotsFile.INSTANCE.newSitemapIndex();

        assertThat(sitemapIndex.getPath(), is("http://www.steambeat.com/sitemap_index_00001.xml"));
        assertThat(RobotsFile.INSTANCE.getSitemapIndexes().size(), is(1));
    }

    @Test
    public void canCreateTwoSitemapIndexes() {
        SitemapIndex sitemapIndex = RobotsFile.INSTANCE.newSitemapIndex();
        SitemapIndex anotherSitemapIndex = RobotsFile.INSTANCE.newSitemapIndex();

        assertThat(RobotsFile.INSTANCE.getSitemapIndexes().size(), is(2));
        assertThat(sitemapIndex.getPath(), is("http://www.steambeat.com/sitemap_index_00001.xml"));
        assertThat(anotherSitemapIndex.getPath(), is("http://www.steambeat.com/sitemap_index_00002.xml"));
    }

    @Test
    public void canGetASitemapIndexFromIndex() {
        RobotsFile.INSTANCE.newSitemapIndex();
        RobotsFile.INSTANCE.newSitemapIndex();
        final SitemapIndex sitemapIndex = RobotsFile.INSTANCE.newSitemapIndex();
        sitemapIndex.newSitemap();

        SitemapIndex found = RobotsFile.INSTANCE.getSitemapIndex(3);
        
        assertThat(found, is(sitemapIndex));
        assertThat(found.getSitemaps().size(), is(1));
    }
}
