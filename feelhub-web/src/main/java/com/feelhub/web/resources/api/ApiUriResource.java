package com.feelhub.web.resources.api;

import com.feelhub.application.UriService;
import com.feelhub.domain.keyword.uri.*;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

public class ApiUriResource extends ServerResource {

    @Inject
    public ApiUriResource(final UriService uriService) {
        this.uriService = uriService;
    }

    @Get
    public ModelAndView getId() {
        final Form parameters = getQuery();
        final String keywordValue = parameters.getFirstValue("keywordValue").toString().trim();
        try {
            final Uri uri = uriService.lookUp(keywordValue);
            setStatus(Status.SUCCESS_OK);
            return ModelAndView.createNew("api/uri.json.ftl", MediaType.APPLICATION_JSON).with("topicId", uri.getTopicId());
        } catch (UriNotFound e) {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return ModelAndView.createNew("api/uri.json.ftl", MediaType.APPLICATION_JSON);
        }
    }

    private UriService uriService;
}
