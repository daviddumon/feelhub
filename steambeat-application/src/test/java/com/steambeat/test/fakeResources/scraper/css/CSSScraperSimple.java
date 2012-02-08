package com.steambeat.test.fakeResources.scraper.css;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class CSSScraperSimple extends ServerResource {

    @Get
    public Representation represent() {
        final String css = "logo { background-url: ('logoUrl');}";
        return new StringRepresentation(css);
    }
}
