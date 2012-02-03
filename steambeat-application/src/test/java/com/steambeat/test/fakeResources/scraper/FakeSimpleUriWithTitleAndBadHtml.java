package com.steambeat.test.fakeResources.scraper;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class FakeSimpleUriWithTitleAndBadHtml extends ServerResource {

    @Get
    public Representation represent() {
        String html = "<html><title>Webpage title<body></body></html>";
        return new StringRepresentation(html);
    }
}
