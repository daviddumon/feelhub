package com.feelhub.web.tools;

import com.feelhub.sitemap.application.SitemapsRepository;

import java.io.InputStream;

public class FeelhubSitemapModuleLink {

    public InputStream get(final String index) {
        return SitemapsRepository.instance().get(index);
    }

}
