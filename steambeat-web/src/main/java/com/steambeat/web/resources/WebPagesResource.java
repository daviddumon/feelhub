package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.web.ReferenceBuilder;
import org.restlet.data.*;
import org.restlet.resource.*;

public class WebPagesResource extends ServerResource {

    @Inject
    public WebPagesResource(final AssociationService associationService, final SubjectService subjectService) {
        this.associationService = associationService;
        this.subjectService = subjectService;
    }

    @Post
    public void post(final Form form) {
        uri = form.getFirstValue("uri");
        final Association association = lookUpAssociation();
        final WebPage webPage = subjectService.addWebPage(association);
        final String uriToRedirect = "/webpages/" + webPage.getSemanticDescription() + "/" + webPage.getId();
        setLocationRef(new ReferenceBuilder(this.getContext()).buildUri(uriToRedirect));
        setStatus(Status.SUCCESS_CREATED);
    }

    private Association lookUpAssociation() {
        Association association;
        try {
            association = associationService.lookUp(new Uri(uri));
        } catch (AssociationNotFound e) {
            association = associationService.createAssociationsFor(new Uri(uri));
        }
        return association;
    }

    private final AssociationService associationService;
    private final SubjectService subjectService;
    private String uri;
}
