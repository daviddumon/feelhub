package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.web.ReferenceBuilder;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.restlet.data.*;
import org.restlet.representation.*;
import org.restlet.resource.*;

public class BookmarkletResource extends ServerResource {

    @Inject
    public BookmarkletResource(final AssociationService associationService) {
        this.associationService = associationService;
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
            association = associationService.lookUp(new Uri(uri));
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
        setLocationRef(new ReferenceBuilder(this.getContext()).buildUri("/webpages/" + association.getSubjectId()));
        return new EmptyRepresentation();
    }

    private final AssociationService associationService;
    private boolean mustCreate = false;
    private String uri;
    private Form form;
    private Association association;
    private static final String currentVersion = "1";
}
