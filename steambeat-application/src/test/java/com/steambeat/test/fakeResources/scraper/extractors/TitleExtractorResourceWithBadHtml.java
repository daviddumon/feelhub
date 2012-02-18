package com.steambeat.test.fakeResources.scraper.extractors;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class TitleExtractorResourceWithBadHtml extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html><title>Webpage title<body></body></html>";
        return new StringRepresentation(html);
    }
}
