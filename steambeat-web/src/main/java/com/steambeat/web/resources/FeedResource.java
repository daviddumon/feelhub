package com.steambeat.web.resources;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.subject.feed.*;
import com.steambeat.web.*;
import com.steambeat.web.search.OpinionSearch;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.List;

public class FeedResource extends ServerResource {

    @Inject
    public FeedResource(final FeedService feedService, OpinionSearch opinionSearch) {
        this.feedService = feedService;
        this.opinionSearch = opinionSearch;
    }

    @Override
    protected void doInit() throws ResourceException {
        uri = new Uri(getRequestAttributes().get("uri").toString());
        try {
            feed = feedService.lookUpFeed(uri);
            preparePage();
        } catch (FeedNotYetCreatedException e) {
            mustCreateFeed = true;
        }
    }

    private void preparePage() {
        if (!feed.getRealUri().equals(uri)) {
            redirectToUri();
        } else {
            pages = Page.forContextAndFeed(getContext(), feed, opinionSearch);
            pageNumber = getPageNumber();
        }
    }

    private void redirectToUri() {
        setStatus(Status.REDIRECTION_PERMANENT);
        setLocationRef(new ReferenceBuilder(getContext()).buildUri("/feeds/" + feed.getUri()));
    }

    private int getPageNumber() {
        final Object parameter = getRequestAttributes().get("page");
        if (parameter != null) {
            final int result = Integer.parseInt(parameter.toString());
            if (result > 0 && result <= this.pages.size()) {
                return result;
            }
        }
        return 1;
    }

    @Get
    public Representation represent() {
        if (mustCreateFeed) {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return SteambeatTemplateRepresentation.createNew("newfeed.ftl", getContext()).with("uri", uri.toString());
        }
        return SteambeatTemplateRepresentation.createNew("feed.ftl", getContext()).with("feed", feed)
                .with("page", page())
                .with("pages", pageNumbers())
                .with("counter", 0);
    }

    private Page page() {
        if (pages.isEmpty()) {
            return new Page();
        }
        return pages.get(pageNumber - 1);
    }

    private List<Integer> pageNumbers() {
        final List<Integer> numbers = Lists.newArrayList();
        for (int i = 0; i < pages.size(); i++) {
            numbers.add(i + 1);
        }
        return numbers;
    }

    private Feed feed;
    private int pageNumber;
    private List<Page> pages;
    private final FeedService feedService;
    private OpinionSearch opinionSearch;
    private Uri uri;
    private boolean mustCreateFeed;
}
