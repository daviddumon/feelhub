package com.feelhub.web.resources.sitemap;

import com.feelhub.web.representation.FeelhubSitemapRepresentation;
import com.feelhub.web.tools.FeelhubSitemapModuleLink;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import javax.inject.Inject;
import java.io.InputStream;

public class FeelhubSitemapResource extends ServerResource {

    @Inject
    public FeelhubSitemapResource(final FeelhubSitemapModuleLink feelhubSitemapModuleLink) {
        this.feelhubSitemapModuleLink = feelhubSitemapModuleLink;
    }

    @Get
    public Representation represent() {
        final String index = getRequestAttributes().get("number").toString();
        final InputStream sitemap = feelhubSitemapModuleLink.get("sitemap_" + index + ".xml");
        return new FeelhubSitemapRepresentation(sitemap);
    }

    private final FeelhubSitemapModuleLink feelhubSitemapModuleLink;
}
