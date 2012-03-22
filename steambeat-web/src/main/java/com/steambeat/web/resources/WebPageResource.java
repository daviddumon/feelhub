package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.application.SubjectService;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.web.SteambeatTemplateRepresentation;
import org.restlet.representation.Representation;
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
    }

    @Get
    public Representation represent() {
        return SteambeatTemplateRepresentation.createNew("webpage.ftl", getContext()).with("webPage", webPage).with("counter", 0);
    }

    private final SubjectService subjectService;
    private UUID id;
    private WebPage webPage;
}
