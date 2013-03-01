package com.feelhub.web.resources;

import com.feelhub.web.representation.ModelAndView;
import org.restlet.resource.*;

public class BookmarkletResource extends ServerResource {

    @Get
    public ModelAndView redirectToTopic() throws Exception {
        return ModelAndView.createNew("bookmarklet.ftl").with("uri", getUri());
    }

    private String getUri() throws Exception {
        if (getQuery().getQueryString().contains("q")) {
            return getQuery().getFirstValue("q");
        } else {
            throw new Exception();
        }
    }
}
