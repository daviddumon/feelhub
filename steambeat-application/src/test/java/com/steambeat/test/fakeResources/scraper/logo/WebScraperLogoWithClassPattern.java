package com.steambeat.test.fakeResources.scraper.logo;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class WebScraperLogoWithClassPattern extends ServerResource {

    @Get
    public Representation represent() {
        String html = "<html><body><img src='http://www.google.fr/images/lol.jpg' /><img class='myLogo_v1' src='http://www.image.com/image.jpg'/></body></html>";
        return new StringRepresentation(html);
    }
}
