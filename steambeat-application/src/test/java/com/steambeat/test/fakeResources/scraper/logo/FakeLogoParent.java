package com.steambeat.test.fakeResources.scraper.logo;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class FakeLogoParent extends ServerResource {

    @Get
    public Representation represent() {
        String html = "<html><body><div id='image' style='background-image:url(http://www.google.fr/images/lol.jpg)'><div id='logo'></div></div></body></html>";
        return new StringRepresentation(html);
    }
}
