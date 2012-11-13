package com.feelhub.test.fakeResources.scraper;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class ScraperLogoPriorityWithOpenGraph extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html>" +
                "<head><meta property='og:image' content='http://i4.ytimg.com/vi/_IOqGclele4/mqdefault.jpg'></head>" +
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
