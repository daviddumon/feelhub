package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.domain.opinion.Feeling;
import com.steambeat.domain.subject.feed.*;
import com.steambeat.web.ReferenceBuilder;
import com.steambeat.web.search.OpinionSearch;
import com.steambeat.application.*;
import org.restlet.data.*;
import org.restlet.resource.*;

public class FeedOpinionsResource extends ServerResource {

    @Inject
    public FeedOpinionsResource(final FeedService feedService, final OpinionService opinionService, OpinionSearch opinionSearch) {
        this.feedService = feedService;
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
        String text = form.getFirstValue("value");
        final Feed feed;
        try {
            feed = feedService.lookUpFeed(uri);
            opinionService.addOpinion(feed, feeling, text);
            setStatus(Status.SUCCESS_CREATED);
            setLocationRef(new ReferenceBuilder(getContext()).buildUri("/feeds/" + uri));
        } catch (FeedNotYetCreatedException e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

    private void checkHasFeeling(Form form) {
        if (form.getFirstValue("feeling") == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

    private final FeedService feedService;
    private OpinionService opinionService;
    private OpinionSearch opinionSearch;
    private Uri uri;
}
