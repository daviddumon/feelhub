package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.application.FeedService;
import com.steambeat.domain.subject.feed.*;
import com.steambeat.web.ReferenceBuilder;
import org.restlet.data.*;
import org.restlet.resource.*;

public class FeedsResource extends ServerResource {

    @Inject
    public FeedsResource(final FeedService feedService) {
        this.feedService = feedService;
    }

    @Post
    public void post(final Form form) {
        final Feed feed = feedService.addFeed(new Uri(form.getFirstValue("uri")));
        setStatus(Status.SUCCESS_CREATED);
        setLocationRef(new ReferenceBuilder(this.getContext()).buildUri("/feeds/" + feed.getUri()));
    }

    private FeedService feedService;
}
