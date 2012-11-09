package com.feelhub.test.fakeResources.scraper.extractors;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class LogoExtractorResourceFromCss extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html><head>" +
                "<title></title>" +
                "<link rel='StyleSheet' href='http://localhost:6162/css/css1' />" +
                "<link rel='Stylesheet' href='http://localhost:6162/css/css2' />" +
                "<body>" +
                "<div id='logo'>" +
                "<span id='logoimage'></span>" +
                "</div>" +
                "</body></html>";

        return new StringRepresentation(html);
    }
}