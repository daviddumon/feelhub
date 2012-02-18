package com.steambeat.test.fakeResources.scraper.extractors;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class LogoExtractorResourceWithIdLogoPattern extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html><body><img src='http://www.google.fr/images/lol.jpg' /><img id='_lol_LOGOV2' src='http://www.image.com/image.jpg'/></body></html>";
        return new StringRepresentation(html);
    }
}
