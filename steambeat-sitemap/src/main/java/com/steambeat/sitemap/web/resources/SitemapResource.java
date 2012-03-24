package com.steambeat.sitemap.web.resources;

import com.steambeat.sitemap.domain.*;
import com.steambeat.sitemap.web.representation.SitemapTemplateRepresentation;
import org.restlet.data.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

public class SitemapResource extends ServerResource {

    @Get
    public Representation represent() {
        try {
            final String index = (String) getRequestAttributes().get("index");
            final Sitemap sitemap = SitemapRepository.getSitemap(Integer.valueOf(index));
            return SitemapTemplateRepresentation.createNew("/sitemap.ftl", getContext(), MediaType.APPLICATION_XML).with("entries", sitemap.getEntries());
        } catch (SitemapNotFoundException e) {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return new EmptyRepresentation();
        }
    }
}
