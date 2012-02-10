package com.steambeat.test.fakeResources.scraper.extractors;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class LastElementExtractorResourceLemondeBug extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<body><h1><p>h1 text</p></h1><h2><a href='lol'>h2 text</a></body>";
        return new StringRepresentation(html);
    }
}
