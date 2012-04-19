package com.steambeat.web.resources;

import com.steambeat.web.tools.SteambeatSitemapModuleLink;
import com.steambeat.web.representation.SteambeatRobotsRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import javax.inject.Inject;
import java.io.InputStream;

public class SteambeatRobotsResource extends ServerResource {

    @Inject
    public SteambeatRobotsResource(final SteambeatSitemapModuleLink steambeatSitemapModuleLink) {
        this.steambeatSitemapModuleLink = steambeatSitemapModuleLink;
    }

    @Get
    public Representation represent() {
        final InputStream robots = steambeatSitemapModuleLink.get("/robots.txt");
        return new SteambeatRobotsRepresentation(robots);
    }

    private final SteambeatSitemapModuleLink steambeatSitemapModuleLink;
}
