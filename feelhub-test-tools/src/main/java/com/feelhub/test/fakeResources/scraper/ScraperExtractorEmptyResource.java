package com.feelhub.test.fakeResources.scraper;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class ScraperExtractorEmptyResource extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html>" +
                "<head>" +
                "</head>" +
                "<body>" +
                "</body>" +
                "</html>";
        return new StringRepresentation(html);
    }
}
