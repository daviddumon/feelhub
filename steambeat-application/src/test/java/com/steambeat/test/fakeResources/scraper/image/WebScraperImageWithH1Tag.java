package com.steambeat.test.fakeResources.scraper.image;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class WebScraperImageWithH1Tag extends ServerResource {

    @Get
    public Representation represent() {
        String html = "<html><body><h1><img src='http://www.google.fr/images/h1.jpg'/></h1></body></html>";
        return new StringRepresentation(html);
    }
}
