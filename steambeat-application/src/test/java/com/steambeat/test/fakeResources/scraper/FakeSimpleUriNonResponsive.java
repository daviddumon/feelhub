package com.steambeat.test.fakeResources.scraper;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class FakeSimpleUriNonResponsive extends ServerResource {

    @Get
    public Representation represent() throws InterruptedException {
        Thread.sleep(3100);
        return new StringRepresentation("<html></html>");
    }
}
