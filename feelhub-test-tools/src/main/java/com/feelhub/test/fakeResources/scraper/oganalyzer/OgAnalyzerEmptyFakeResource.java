package com.feelhub.test.fakeResources.scraper.oganalyzer;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class OgAnalyzerEmptyFakeResource extends ServerResource {

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