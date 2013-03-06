package com.feelhub.sitemap.domain;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class SitemapIndexUriTest {
    @Test
    public void canGetUri() {
        String uri = SitemapIndexUri.getUri(123);

        assertThat(uri).isEqualTo("/sitemap_index_00123.xml");
    }
}
