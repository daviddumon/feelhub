package com.feelhub.web.resources;

import com.feelhub.web.representation.ModelAndView;
import org.restlet.data.Status;
import org.restlet.resource.*;

public class SearchResource extends ServerResource {

    @Get
    public ModelAndView search() {
        final String query = getRequestAttributes().get("q").toString().trim();
        setStatus(Status.SUCCESS_OK);
        return ModelAndView.createNew("search.ftl").with("q", query);
    }
}
