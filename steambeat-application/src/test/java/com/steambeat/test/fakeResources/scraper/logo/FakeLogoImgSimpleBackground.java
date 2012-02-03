package com.steambeat.test.fakeResources.scraper.logo;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class FakeLogoImgSimpleBackground extends ServerResource {

    @Get
    public Representation represent() {
        String html = "<html><body><img src='http://www.google.fr/images/lol.jpg' /><img class='logo' style='background-image:url(http://www.logo.com/logo.jpg)'/></body></html>";
        return new StringRepresentation(html);
    }
}
