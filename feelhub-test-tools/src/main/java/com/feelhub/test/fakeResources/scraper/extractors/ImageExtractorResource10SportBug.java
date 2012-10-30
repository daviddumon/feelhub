package com.feelhub.test.fakeResources.scraper.extractors;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class ImageExtractorResource10SportBug extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<body><div id='visu'><img src='http://www.10sport.com/image.jpg'/></div><h2>sport</h2></body>";
        return new StringRepresentation(html);
    }
}
