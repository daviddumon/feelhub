package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.opinion.Feeling;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.web.ReferenceBuilder;
import com.steambeat.web.search.OpinionSearch;
import org.restlet.data.*;
import org.restlet.resource.*;

public class WebPageOpinionsResource extends ServerResource {

    @Inject
    public WebPageOpinionsResource(final WebPageService webPageService, final OpinionService opinionService, final OpinionSearch opinionSearch) {
        this.webPageService = webPageService;
        this.opinionService = opinionService;
        this.opinionSearch = opinionSearch;
    }

    @Override
    protected void doInit() throws ResourceException {
        uri = new Uri(Reference.decode(getRequestAttributes().get("uri").toString()));
    }

    @Post
    public void post(final Form form) {
        checkHasFeeling(form);
        final Feeling feeling = Feeling.valueOf(form.getFirstValue("feeling"));
        final String text = form.getFirstValue("text");
        final WebPage webPage;
        try {
            webPage = webPageService.lookUpWebPage(uri);
            opinionService.addOpinion(webPage, feeling, text);
            setStatus(Status.SUCCESS_CREATED);
            setLocationRef(new ReferenceBuilder(getContext()).buildUri("/webpages/" + uri));
        } catch (WebPageNotYetCreatedException e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

    private void checkHasFeeling(final Form form) {
        if (form.getFirstValue("feeling") == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

    private final WebPageService webPageService;
    private final OpinionService opinionService;
    private OpinionSearch opinionSearch;
    private Uri uri;
}
