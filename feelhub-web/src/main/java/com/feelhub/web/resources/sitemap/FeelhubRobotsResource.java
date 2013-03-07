package com.feelhub.web.resources.sitemap;

import com.feelhub.web.representation.FeelhubRobotsRepresentation;
import com.feelhub.web.tools.FeelhubSitemapModuleLink;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import javax.inject.Inject;
import java.io.InputStream;

public class FeelhubRobotsResource extends ServerResource {

    @Inject
    public FeelhubRobotsResource(final FeelhubSitemapModuleLink feelhubSitemapModuleLink) {
        this.feelhubSitemapModuleLink = feelhubSitemapModuleLink;
    }

    @Get
    public Representation represent() {
        return new FeelhubRobotsRepresentation(feelhubSitemapModuleLink.get("robots.txt"));
    }

    private final FeelhubSitemapModuleLink feelhubSitemapModuleLink;
}
