package com.steambeat.test.fakeResources.scraper;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class WebScraperWithTitleTag extends ServerResource {

    @Get
    public Representation represent() {
        String html = "<html><head><title>Webpage title</title></head><body></body></html>";
        return new StringRepresentation(html);
    }
}
