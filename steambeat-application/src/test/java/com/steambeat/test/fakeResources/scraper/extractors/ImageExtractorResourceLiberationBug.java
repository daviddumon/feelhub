package com.steambeat.test.fakeResources.scraper.extractors;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class ImageExtractorResourceLiberationBug extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html>" +
                "<body>" +
                "<img src='bad.jpg' />" +
                "<img id='logo' src='logo.jpg'/>" +
                "<div>" +
                "<h1></h1>" +
                "</div>" +
                "<img src='http://www.liberation.fr/image.jpg'/>" +
                "</body>" +
                "</html>";
        return new StringRepresentation(html);
    }
}
