package com.steambeat.test.fakeResources.scraper;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class FakeSimpleUriWithImage extends ServerResource {

    @Get
    public Representation represent() {
        String html = "<html><body><img src='http://www.google.fr/images/lol.jpg' /></body></html>";
        return new StringRepresentation(html);
    }
}
