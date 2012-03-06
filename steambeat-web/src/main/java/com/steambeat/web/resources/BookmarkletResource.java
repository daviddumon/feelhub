package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.web.*;
import org.restlet.data.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

public class BookmarkletResource extends ServerResource {

    @Inject
    public BookmarkletResource(final AssociationService associationService, final WebPageService webPageService) {
        this.associationService = associationService;
        this.webPageService = webPageService;
    }

    @Override
    protected void doInit() throws ResourceException {
        try {
            lookUpWebpage();
        } catch (AssociationNotFound e) {
            mustCreate = true;
        }
    }

    private void lookUpWebpage() {
        final Form form = getQuery();
        final String queryString = form.getQueryString();
        if (queryString.contains("q")) {
            uri = form.getFirstValue("q").trim();
            final Association association = associationService.lookUp(new Uri(uri));
            webPage = webPageService.lookUpWebPage(association.getSubjectId());
        } else {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

    @Get
    public Representation represent() {
        if (mustCreate) {
            setStatus(Status.SUCCESS_OK);
            return SteambeatTemplateRepresentation.createNew("newsubject.ftl", getContext()).with("uri", uri);
        }
        setStatus(Status.REDIRECTION_SEE_OTHER);
        setLocationRef(new ReferenceBuilder(this.getContext()).buildUri("/webpages/" + webPage.getSemanticDescription() + "/" + webPage.getId()));
        return new EmptyRepresentation();
    }

    private AssociationService associationService;
    private WebPageService webPageService;
    private WebPage webPage;
    private boolean mustCreate = false;
    private String uri;
}
