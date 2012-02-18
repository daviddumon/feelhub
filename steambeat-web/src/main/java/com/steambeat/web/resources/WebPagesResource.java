package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.application.WebPageService;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.web.ReferenceBuilder;
import org.restlet.data.*;
import org.restlet.resource.*;

public class WebPagesResource extends ServerResource {

    @Inject
    public WebPagesResource(final WebPageService webPageService) {
        this.webPageService = webPageService;
    }

    @Post
    public void post(final Form form) {
        final WebPage webPage = webPageService.addWebPage(new Uri(form.getFirstValue("uri")));
        setStatus(Status.SUCCESS_CREATED);
        setLocationRef(new ReferenceBuilder(this.getContext()).buildUri("/webpages/" + webPage.getId()));
    }

    private final WebPageService webPageService;
}
