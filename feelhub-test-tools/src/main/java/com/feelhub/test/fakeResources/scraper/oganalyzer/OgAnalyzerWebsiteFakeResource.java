package com.feelhub.test.fakeResources.scraper.oganalyzer;

import org.restlet.representation.*;
import org.restlet.resource.*;

public class OgAnalyzerWebsiteFakeResource extends ServerResource {

    @Get
    public Representation represent() {
        final String html = "<html>" +
                "<head>" +
                "<meta property=\"og:type\" content=\"website\">" +
                "<meta property=\"og:title\" content=\"Le Monde.fr - Actualité à la Une\">" +
                "<meta property=\"og:site_name\" content=\"Le Monde.fr\">" +
                "<meta property=\"og:description\" content=\"Le Monde.fr - 1er site d'information. Les articles du journal et toute l'actualité en continu : International, France, Société, Economie, Culture, Environnement, Blogs ...\">" +
                "<meta property=\"og:url\" content=\"http://www.lemonde.fr\">" +
                "<meta property=\"og:locale\" content=\"fr_FR\">" +
                "</head>" +
                "<body>" +
                "</body>" +
                "</html>";
        return new StringRepresentation(html);
    }
}
