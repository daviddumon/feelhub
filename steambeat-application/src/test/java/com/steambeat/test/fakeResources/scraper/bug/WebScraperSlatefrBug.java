package com.steambeat.test.fakeResources.scraper.bug;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class WebScraperSlatefrBug extends ServerResource {

    @Get
    public Representation represent() {
        String html = "<h1>Si Marine le Pen n'a pas les signatures, le jeu reste ouvert pour Sarkozy</h1>" +
                "'<div class='article_meta'>" +
                "<span class='article_author'>Par <a href='/source/jean-marie-colombani'>Jean-Marie Colombani</a></span>" +
                "<span class='article_date'> | publi&eacute; le 4 f&eacute;vrier 2012</span>" +
                "</div>" +
                "<div class='article_content'>" +
                "<div class='article_image'></div>" +
                "<img  class='imagefield imagefield-field_grande_image' width='600' height='330' title='Nicolas Sarkozy le 29 janvier 2012. REUTERS/Lionel Bonaventure/POOL' alt='' src='http://www.slate.fr/sites/default/files/sarkozy-tv_0.jpg?1328353776' /></div>";

        return new StringRepresentation(html);
    }
}
