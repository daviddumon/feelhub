package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.web.*;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

public class SubjectsResource extends ServerResource {

    @Inject
    public SubjectsResource(final AssociationService associationService, final WebPageService webPageService) {
        this.associationService = associationService;
        this.webPageService = webPageService;
    }

    @Get
    public Representation represent() {
        doFormExtract();
        return SteambeatTemplateRepresentation.createNew("json/subject.json.ftl", getContext()).with("uri", new ReferenceBuilder(getContext()).buildUri("/webpages/" + "semantic/" + webPage.getId()));
    }

    private void doFormExtract() {
        final Form form = getQuery();
        final String queryString = form.getQueryString();
        if (queryString.contains("q")) {
            final String uri = form.getFirstValue("q").trim();
            final Association association = associationService.lookUp(new Uri(uri));
            webPage = webPageService.lookUpWebPage(association.getSubjectId());
        }
    }

    private AssociationService associationService;
    private WebPageService webPageService;
    private WebPage webPage;
}
