package com.steambeat.test.fakeResources.scraper;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class UriScraperLogoPriority extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html>" +
                            "<body>" +
                            "<img src='bad.jpg' />" +
                            "<img id='logo' src='logo.jpg'/>" +
                            "<h1></h1>" +
                            "<img src='image.jpg'/>" +
                            "</body>" +
                            "</html>";
        return new StringRepresentation(html);
    }
}
