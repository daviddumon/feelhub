package com.steambeat.test.fakeResources.scraper.logo;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class WebScraperLogoFromNested extends ServerResource {

    @Get
    public Representation represent() {
        String html = "<html><body><div id='logo'><img src='http://www.google.fr/images/lol.jpg'/></div></body></html>";
        return new StringRepresentation(html);
    }
}
