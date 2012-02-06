package com.steambeat.test.fakeResources.scraper;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class WebScraperWithH2Tag extends ServerResource {

    @Get
    public Representation represent() {
        String html = "<html><head><title></title></head><body><h2>First h2 section</h2><h3>First h3 section</h3></body></html>";
        return new StringRepresentation(html);
    }
}
