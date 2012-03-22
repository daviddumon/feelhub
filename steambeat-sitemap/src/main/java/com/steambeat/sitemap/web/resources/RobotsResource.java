package com.steambeat.sitemap.web.resources;

import com.steambeat.sitemap.web.representation.SitemapTemplateRepresentation;
import org.restlet.data.MediaType;
import org.restlet.representation.*;
import org.restlet.resource.*;

public class RobotsResource extends ServerResource {

    @Get
    public Representation represent() {
        return SitemapTemplateRepresentation.createNew("/robots.ftl", getContext(), MediaType.TEXT_PLAIN);
    }
}
