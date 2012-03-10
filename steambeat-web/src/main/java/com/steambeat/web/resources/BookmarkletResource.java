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

import java.util.UUID;

public class BookmarkletResource extends ServerResource {

    @Inject
    public BookmarkletResource(final AssociationService associationService, final WebPageService webPageService) {
        this.associationService = associationService;
        this.webPageService = webPageService;
    }

    @Override
    protected void doInit() throws ResourceException {
        form = getQuery();
        try {
            final String queryString = form.getQueryString();
            checkContainsVersion(queryString);
            checkContainsUri(queryString);
        } catch (AssociationNotFound e) {
            mustCreate = true;
        }
    }

    private void checkContainsVersion(final String queryString) {
        if (queryString.contains("version")) {
            checkForVersion(form.getFirstValue("version").trim());
        } else {
            throw new BookmarkletBadVersion();
        }
    }

    private void checkForVersion(final String version) {
        if (!version.equals(currentVersion)) {
            throw new BookmarkletBadVersion();
        }
    }

    private void checkContainsUri(final String queryString) {
        if (queryString.contains("q")) {
            uri = form.getFirstValue("q").trim();
            if (uri.isEmpty()) {
                throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
            }
            final Association association = associationService.lookUp(new Uri(uri));
            webPage = webPageService.lookUpWebPage(UUID.fromString(association.getSubjectId()));
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

    private final AssociationService associationService;
    private final WebPageService webPageService;
    private WebPage webPage;
    private boolean mustCreate = false;
    private String uri;
    private Form form;
    private static final String currentVersion = "1";
}
