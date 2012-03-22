package com.steambeat.sitemap.web.resources;

import com.google.common.collect.Lists;
import com.steambeat.sitemap.domain.*;
import com.steambeat.sitemap.web.representation.SitemapTemplateRepresentation;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.List;

public class SitemapIndexResource extends ServerResource {

    @Get
    public Representation represent() {
        final String index = (String) getRequest().getAttributes().get("index");
        final SitemapIndex sitemapIndex = RobotsFile.INSTANCE.getSitemapIndex(Integer.valueOf(index));
        if (sitemapIndex != null) {
            sitemaps = sitemapIndex.getSitemaps();
        }
        return SitemapTemplateRepresentation.createNew("/sitemap_index.ftl", getContext(), MediaType.APPLICATION_XML).with("sitemaps", sitemaps);
    }

    private List<Sitemap> sitemaps = Lists.newArrayList();
}
