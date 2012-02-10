package com.steambeat.test.fakeResources.scraper.logo;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class WebScraperLogoWithIdPattern extends ServerResource {

    @Get
    public Representation represent() {
        String html = "<html><body><img src='http://www.google.fr/images/lol.jpg' /><img id='_lol_LOGOV2' src='http://www.image.com/image.jpg'/></body></html>";
        return new StringRepresentation(html);
    }
}