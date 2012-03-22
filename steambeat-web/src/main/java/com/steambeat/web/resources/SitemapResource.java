package com.steambeat.web.resources;

import com.steambeat.tools.Hiram;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import javax.inject.Inject;
import java.io.InputStream;

public class SitemapResource extends ServerResource {

    @Inject
    public SitemapResource(final Hiram hiram) {
        this.hiram = hiram;
    }

    @Get
    public Representation represent() {
        final String index = getRequestAttributes().get("number").toString();
        final InputStream sitemap = hiram.getSitemap(index);
        return new HiramRepresentation(sitemap);
    }

    private final Hiram hiram;
}
