package com.steambeat.test.fakeResources.scraper.extractors;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class LogoExtractorResourceWithBackgroundImage extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html><body><img src='http://www.google.fr/images/lol.jpg' /><img class='logo' style='background-image:url(http://www.image.com/image.jpg)'/></body></html>";
        return new StringRepresentation(html);
    }
}
