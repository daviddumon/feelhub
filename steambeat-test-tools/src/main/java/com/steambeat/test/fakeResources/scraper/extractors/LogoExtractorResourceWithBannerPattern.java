package com.steambeat.test.fakeResources.scraper.extractors;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class LogoExtractorResourceWithBannerPattern extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html><body><img src='http://www.google.fr/images/lol.jpg' /><img id='banner' src='http://www.image.com/good.jpg'/></body></html>";
        return new StringRepresentation(html);
    }
}
