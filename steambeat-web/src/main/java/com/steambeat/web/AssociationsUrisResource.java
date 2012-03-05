package com.steambeat.web;

import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import org.restlet.data.Status;
import org.restlet.representation.*;
import org.restlet.resource.*;

public class AssociationsUrisResource extends ServerResource {

    @Inject
    public AssociationsUrisResource(final AssociationService associationService) {
        this.associationService = associationService;
    }

    @Override
    protected void doInit() throws ResourceException {
        if (getQuery().getQueryString().contains("uri")) {
            lookUpAssociation(new Uri(getQuery().getFirstValue("uri").trim()));
        } else {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

    private void lookUpAssociation(final Uri uri) {
        try {
            association = associationService.lookUp(uri);
        } catch (AssociationNotFound e) {
            mustCreateSubject = true;
        }
    }

    @Get
    public Representation represent() {
        if (mustCreateSubject) {
            return SteambeatTemplateRepresentation.createNew("newsubject.ftl", getContext()).with("id", association.getId());
        } else {
            return new StringRepresentation(association.getSubjectId().toString());
        }
    }

    private AssociationService associationService;
    private boolean mustCreateSubject;
    private Association association;
}
