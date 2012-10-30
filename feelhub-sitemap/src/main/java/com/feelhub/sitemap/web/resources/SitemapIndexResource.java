package com.feelhub.sitemap.web.resources;

import com.feelhub.sitemap.domain.*;
import com.feelhub.sitemap.web.representation.SitemapTemplateRepresentation;
import org.restlet.data.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

public class SitemapIndexResource extends ServerResource {

    @Get
    public Representation represent() {
        try {
            final String index = (String) getRequest().getAttributes().get("index");
            final SitemapIndex sitemapIndex = SitemapIndexRepository.getSitemapIndex(Integer.valueOf(index));
            return SitemapTemplateRepresentation.createNew("/sitemap_index.ftl", getContext(), MediaType.APPLICATION_XML).with("sitemaps", sitemapIndex.getSitemaps());
        } catch (SitemapIndexNotFoundException e) {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return new EmptyRepresentation();
        }
    }
}
