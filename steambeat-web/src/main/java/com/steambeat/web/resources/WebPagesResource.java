package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.web.ReferenceBuilder;
import org.restlet.data.*;
import org.restlet.resource.*;

public class WebPagesResource extends ServerResource {

    @Inject
    public WebPagesResource(final AssociationService associationService, final WebPageService webPageService) {
        this.associationService = associationService;
        this.webPageService = webPageService;
    }

    @Post
    public void post(final Form form) {
        uri = form.getFirstValue("uri");
        final WebPage webPage = webPageService.addWebPage(new Uri(uri));
        final String uriToRedirect = "/webpages/" + webPage.getSemanticDescription() + "/" + webPage.getId();
        setLocationRef(new ReferenceBuilder(this.getContext()).buildUri(uriToRedirect));
        setStatus(Status.SUCCESS_CREATED);
    }

    private final AssociationService associationService;
    private final WebPageService webPageService;
    private String uri;
}
