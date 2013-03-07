package com.feelhub.web.resources.sitemap;

import com.feelhub.web.representation.FeelhubSitemapRepresentation;
import com.feelhub.web.tools.FeelhubSitemapModuleLink;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import javax.inject.Inject;
import java.io.InputStream;

public class FeelhubSitemapIndexResource extends ServerResource {

    @Inject
    public FeelhubSitemapIndexResource(final FeelhubSitemapModuleLink feelhubSitemapModuleLink) {
        this.feelhubSitemapModuleLink = feelhubSitemapModuleLink;
    }

    @Get
    public Representation represent() {
        final String index = getRequestAttributes().get("number").toString();
        final InputStream sitemapIndex = feelhubSitemapModuleLink.get("sitemap_index_" + index + ".xml");
        return new FeelhubSitemapRepresentation(sitemapIndex);
    }

    private final FeelhubSitemapModuleLink feelhubSitemapModuleLink;
}
