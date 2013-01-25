package com.feelhub.test.fakeResources.scraper;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class ScraperFakeResource extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html lang='fr'>" +
                "<head>" +
                "<meta name='description' content='description meta'/>" +
                "<meta name='subject' content='subject meta'/>" +
                "<meta name='topic' content='topic meta'/>" +
                "<meta name='summary' content='summary meta'/>" +
                "<meta name='author' content='author'/>" +
                "<meta name='designer' content='designer'/>" +
                "<meta name='owner' content='owner'/>" +
                "<meta http-equiv='Content-Language' content='en'/>" +
                "<meta property='og:title' content='name og'/>" +
                "<title>name title</title>" +
                "</head>" +
                "<body>" +
                "<h1>name h1</h1>" +
                "<h2>name h2</h2>" +
                "<h3>name h3</h3>" +
                "</body>" +
                "</html>";
        return new StringRepresentation(html);
    }
}
