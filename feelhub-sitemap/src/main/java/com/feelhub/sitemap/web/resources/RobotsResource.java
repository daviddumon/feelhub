package com.feelhub.sitemap.web.resources;

import com.google.common.collect.Lists;
import com.feelhub.sitemap.domain.*;
import com.feelhub.sitemap.web.representation.SitemapTemplateRepresentation;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.List;

public class RobotsResource extends ServerResource {

    @Get
    public Representation represent() {
        indexes = SitemapIndexRepository.getSitemapIndexes();
        return SitemapTemplateRepresentation.createNew("/robots.ftl", getContext(), MediaType.TEXT_PLAIN).with("indexes", indexes);
    }

    private List<SitemapIndex> indexes = Lists.newArrayList();
}
