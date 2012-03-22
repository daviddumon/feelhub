package com.steambeat.test.fakeResources.scraper.extractors;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class TitleExtractorResourceWithTitleTag extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html><head><title>Webpage title</title></head><body></body></html>";
        return new StringRepresentation(html);
    }
}
