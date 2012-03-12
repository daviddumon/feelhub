package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.application.SubjectService;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.web.*;
import org.restlet.data.Status;
import org.restlet.representation.*;
import org.restlet.resource.*;

import java.util.UUID;

public class WebPageResource extends ServerResource {

    @Inject
    public WebPageResource(final SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    protected void doInit() throws ResourceException {
        id = UUID.fromString(getRequestAttributes().get("id").toString());
        webPage = subjectService.lookUpWebPage(id);
        semantic = getRequestAttributes().get("semantic").toString();
        if (!semantic.equals(webPage.getSemanticDescription())) {
            mustRedirect = true;
        }
    }

    @Get
    public Representation represent() {
        if (mustRedirect) {
            setStatus(Status.REDIRECTION_PERMANENT);
            final String uriToRedirect = new ReferenceBuilder(getContext()).buildUri("/webpages/" + webPage.getSemanticDescription() + "/" + webPage.getId());
            setLocationRef(uriToRedirect);
            return new EmptyRepresentation();
        } else {
            return SteambeatTemplateRepresentation.createNew("webpage.ftl", getContext()).with("webPage", webPage).with("counter", 0);
        }
    }

    private final SubjectService subjectService;
    private UUID id;
    private WebPage webPage;
    private String semantic;
    private boolean mustRedirect = false;
}
