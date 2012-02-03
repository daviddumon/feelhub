package com.steambeat.test.fakeResources.scraper;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class FakeSimpleUriWithH1Section extends ServerResource {

    @Get
    public Representation represent() {
        String html = "<html><head><title>Webpage title</title></head><body><h1>First section</h1><h1>Second section</h1></body></html>";
        return new StringRepresentation(html);
    }
}
