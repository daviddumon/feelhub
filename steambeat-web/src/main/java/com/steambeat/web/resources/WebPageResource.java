package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.web.*;
import com.steambeat.web.search.OpinionSearch;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

public class WebPageResource extends ServerResource {

    @Inject
    public WebPageResource(final WebPageService webPageService, final OpinionSearch opinionSearch) {
        this.webPageService = webPageService;
        this.opinionSearch = opinionSearch;
    }

    @Override
    protected void doInit() throws ResourceException {
        uri = new Uri(getRequestAttributes().get("uri").toString());
        try {
            webPage = webPageService.lookUpWebPage(uri);
            if (!webPage.getRealUri().equals(uri)) {
                redirectToUri();
            }
        } catch (WebPageNotYetCreatedException e) {
            mustCreateFeed = true;
        }
    }

    private void redirectToUri() {
        setStatus(Status.REDIRECTION_PERMANENT);
        setLocationRef(new ReferenceBuilder(getContext()).buildUri("/webpages/" + webPage.getId()));
    }

    @Get
    public Representation represent() {
        if (mustCreateFeed) {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return SteambeatTemplateRepresentation.createNew("newwebpage.ftl", getContext())
                    .with("uri", uri.toString());
        }
        return SteambeatTemplateRepresentation.createNew("webpage.ftl", getContext())
                .with("webPage", webPage)
                .with("counter", 0);
    }

    private final WebPageService webPageService;
    private final OpinionSearch opinionSearch;
    private Uri uri;
    private WebPage webPage;
    private boolean mustCreateFeed;
}
