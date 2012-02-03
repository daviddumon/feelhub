package com.steambeat.test.fakeResources.scraper;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class FakeSimpleUriWithTitle extends ServerResource {

    @Get
    public Representation represent() {
        String html = "<html><head><title>Webpage title</title></head><body></body></html>";
        return new StringRepresentation(html);
    }
}
