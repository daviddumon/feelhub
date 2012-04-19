package com.steambeat.web.resources;

import com.steambeat.web.tools.SteambeatSitemapModuleLink;
import com.steambeat.web.representation.SteambeatSitemapRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import javax.inject.Inject;
import java.io.InputStream;

public class SteambeatSitemapIndexResource extends ServerResource {

    @Inject
    public SteambeatSitemapIndexResource(final SteambeatSitemapModuleLink steambeatSitemapModuleLink) {
        this.steambeatSitemapModuleLink = steambeatSitemapModuleLink;
    }

    @Get
    public Representation represent() {
        final String index = getRequestAttributes().get("number").toString();
        final InputStream sitemapIndex = steambeatSitemapModuleLink.get("/sitemap_index_" + index + ".xml");
        return new SteambeatSitemapRepresentation(sitemapIndex);
    }

    private final SteambeatSitemapModuleLink steambeatSitemapModuleLink;
}
