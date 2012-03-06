package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.application.WebPageService;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.web.*;
import org.restlet.data.Status;
import org.restlet.representation.*;
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

    private final WebPageService webPageService;
    private UUID id;
    private WebPage webPage;
    private String semantic;
    private boolean mustRedirect = false;
}
