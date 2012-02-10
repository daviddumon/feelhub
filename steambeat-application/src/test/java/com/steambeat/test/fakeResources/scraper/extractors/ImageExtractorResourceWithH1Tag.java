package com.steambeat.test.fakeResources.scraper.extractors;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class ImageExtractorResourceWithH1Tag extends ServerResource {

    @Get
    public Representation represent() {
        String html = "<html><body><h1></h1><div></div><img src='http://www.google.fr/images/h1.jpg'/></body></html>";
        return new StringRepresentation(html);
    }
}