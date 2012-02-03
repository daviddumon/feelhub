package com.steambeat.test.fakeResources.scraper;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class FakeSimpleUriWithoutTitle extends ServerResource {

    @Get
    public Representation represent() {
        String html = "<html><head></head><body></body></html>";
        return new StringRepresentation(html);
    }
}
