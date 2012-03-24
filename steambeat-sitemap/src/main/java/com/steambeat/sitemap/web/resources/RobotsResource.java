package com.steambeat.sitemap.web.resources;

import com.google.common.collect.Lists;
import com.steambeat.sitemap.domain.*;
import com.steambeat.sitemap.web.representation.SitemapTemplateRepresentation;
import org.restlet.data.MediaType;
import org.restlet.representation.*;
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
