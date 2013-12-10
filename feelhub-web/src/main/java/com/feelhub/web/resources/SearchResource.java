package com.feelhub.web.resources;

import com.feelhub.domain.feeling.FeelingValue;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.base.Optional;
import org.restlet.resource.*;

public class SearchResource extends ServerResource {


    @Get
    public ModelAndView represent() {
        return ModelAndView
                .createNew("search.ftl")
                .with("feelingValues", FeelingValue.values())
                .with("query", Optional.fromNullable(getQueryValue("q")).or(""));
    }
}
