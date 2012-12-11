package com.feelhub.web.resources;

import com.feelhub.domain.topic.TopicIdentifier;
import com.feelhub.web.representation.ModelAndView;
import org.restlet.data.Status;
import org.restlet.resource.*;

public class SearchResource extends ServerResource {

    @Get
    public ModelAndView search() {
        final String query;
        try {
            query = getSearchQuery();
            setStatus(Status.SUCCESS_OK);
            if (TopicIdentifier.isWebTopic(query)) {
                return ModelAndView.createNew("search.ftl").with("q", query).with("type", "web");
            } else {
                return ModelAndView.createNew("search.ftl").with("q", query).with("type", "real");
            }
        } catch (Exception e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return ModelAndView.createNew("error.ftl");
        }
    }

    private String getSearchQuery() throws Exception {
        if (getQuery().getQueryString().contains("q")) {
            return getQuery().getFirstValue("q");
        } else {
            throw new Exception();
        }
    }
}
