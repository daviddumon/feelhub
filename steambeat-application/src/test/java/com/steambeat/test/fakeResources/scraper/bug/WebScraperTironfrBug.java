package com.steambeat.test.fakeResources.scraper.bug;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class WebScraperTironfrBug extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<div id='logo-title'>" +
        "<a href='/' title='Accueil' rel='home' id='logo'>" +
        "<img src='/sites/default/files/tiron_logo.png' alt='Accueil'/>" +
        "</a>" +
        "<div id='name-and-slogan'>" +
        "<div id='site-slogan'>L'auxiliaire de justice nomade</div>" +
        "</div> <!-- /name-and-slogan -->" +
        "</div> <!-- /logo-title -->";

        return new StringRepresentation(html);
    }
}
