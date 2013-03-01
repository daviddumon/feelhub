package com.feelhub.sitemap.web.resources;

import com.feelhub.sitemap.domain.*;
import com.feelhub.sitemap.web.representation.SitemapTemplateRepresentation;
import com.google.common.collect.Lists;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.List;

public class RobotsResource extends ServerResource {

    @Get
    public Representation represent() {
        final List<SitemapIndex> indexes = SitemapIndexRepository.getSitemapIndexes();
        return SitemapTemplateRepresentation.createNew("/robots.ftl", getContext(), MediaType.TEXT_PLAIN).with("indexes", indexes);
    }

}
