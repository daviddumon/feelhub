package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.application.WebPageService;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.web.SteambeatTemplateRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.UUID;

public class WebPageResource extends ServerResource {

    @Inject
    public WebPageResource(final WebPageService webPageService) {
        this.webPageService = webPageService;
    }

    @Override
    protected void doInit() throws ResourceException {
        id = UUID.fromString(getRequestAttributes().get("id").toString());
        webPage = webPageService.lookUpWebPage(id);
    }

    @Get
    public Representation represent() {
        return SteambeatTemplateRepresentation.createNew("webpage.ftl", getContext())
                .with("webPage", webPage)
                .with("counter", 0);
    }

    private final WebPageService webPageService;
    private UUID id;
    private WebPage webPage;
}
