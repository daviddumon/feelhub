package com.steambeat.web.resources;

import com.steambeat.tools.SitemapLink;
import com.steambeat.web.representation.SitemapRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import javax.inject.Inject;
import java.io.InputStream;

public class SteambeatSitemapResource extends ServerResource {

    @Inject
    public SteambeatSitemapResource(final SitemapLink sitemapLink) {
        this.sitemapLink = sitemapLink;
    }

    @Get
    public Representation represent() {
        final String index = getRequestAttributes().get("number").toString();
        final InputStream sitemap = sitemapLink.getSitemap(index);
        return new SitemapRepresentation(sitemap);
    }

    private final SitemapLink sitemapLink;
}
