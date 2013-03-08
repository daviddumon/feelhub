package com.feelhub.web.tools;

import com.feelhub.sitemap.application.SitemapsRepository;
import com.feelhub.sitemap.domain.SitemapIndex;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.util.List;

import static org.fest.assertions.Assertions.*;

public class FeelhubSitemapModuleLinkTest {

    @Test
    public void canGetSitemap() throws Exception {
        SitemapsRepository.initialize(new FakeSitemapsRepository());
        final FeelhubSitemapModuleLink feelhubSitemapModuleLink = new FeelhubSitemapModuleLink();

        final InputStream stream = feelhubSitemapModuleLink.get("00001");

        assertThat(IOUtils.toString(stream)).isEqualTo("00001");
    }

    private class FakeSitemapsRepository extends SitemapsRepository {
        @Override
        public void put(List<SitemapIndex> sitemapIndexes) {

        }

        @Override
        public InputStream get(String objectKey) {
            return new ByteArrayInputStream(objectKey.getBytes());
        }
    }
}
