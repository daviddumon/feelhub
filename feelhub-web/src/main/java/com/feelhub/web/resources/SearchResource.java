package com.feelhub.web.resources;

import com.feelhub.web.representation.ModelAndView;
import com.google.common.base.Optional;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class SearchResource extends ServerResource {


    @Get
    public ModelAndView represent() {
        return ModelAndView
                .createNew("search.ftl")
                .with("query", Optional.fromNullable(getQueryValue("q")).or(""));
    }
}
