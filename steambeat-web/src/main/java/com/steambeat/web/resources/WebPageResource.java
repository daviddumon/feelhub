package com.steambeat.web.resources;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.subject.webpage.*;
import com.steambeat.web.*;
import com.steambeat.web.search.OpinionSearch;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.List;

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
            preparePage();
        } catch (WebPageNotYetCreatedException e) {
            mustCreateFeed = true;
        }
    }

    private void preparePage() {
        if (!webPage.getRealUri().equals(uri)) {
            redirectToUri();
        } else {
            pages = Page.forContextAndSubject(getContext(), webPage, opinionSearch);
            pageNumber = getPageNumber();
        }
    }

    private void redirectToUri() {
        setStatus(Status.REDIRECTION_PERMANENT);
        setLocationRef(new ReferenceBuilder(getContext()).buildUri("/webpages/" + webPage.getUri()));
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
            return SteambeatTemplateRepresentation.createNew("newwebpage.ftl", getContext()).with("uri", uri.toString());
        }
        return SteambeatTemplateRepresentation.createNew("webpage.ftl", getContext()).with("webPage", webPage)
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

    private WebPage webPage;
    private int pageNumber;
    private List<Page> pages;
    private final WebPageService webPageService;
    private final OpinionSearch opinionSearch;
    private Uri uri;
    private boolean mustCreateFeed;
}
