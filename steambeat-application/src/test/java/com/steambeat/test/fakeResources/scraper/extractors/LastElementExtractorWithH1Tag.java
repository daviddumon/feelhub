package com.steambeat.test.fakeResources.scraper.extractors;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class LastElementExtractorWithH1Tag extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html><head></head><body><h1>First section</h1><h1>Second section</h1></body></html>";
        return new StringRepresentation(html);
    }
}
